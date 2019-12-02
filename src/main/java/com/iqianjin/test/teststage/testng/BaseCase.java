package com.iqianjin.test.teststage.testng;

import com.alibaba.fastjson.*;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.utils.AssertUtil;
import com.iqianjin.test.teststage.utils.NewHttpClientUtil;
import com.iqianjin.test.teststage.utils.RedisUtils;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Listeners({AutoTestListener.class, RetryListener.class})
@Slf4j
public class BaseCase {


    private static Map<Object, Object> targetDataSources = new HashMap<>();

    public NewHttpClientUtil httpClientUtil = NewHttpClientUtil.getInstance();

    public static JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public static StringRedisTemplate saoBingRedisTemplate = new StringRedisTemplate();

    String url  = "jdbc:mysql://10.10.120.105:3306/qianjin?useUnicode=true&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";
    String mysqlUserName = "qianjin20160304";
    String mysqlPassWord = "qianjin20160304";
    String driveClass = "com.mysql.jdbc.Driver";
    {
        try {
            org.apache.tomcat.jdbc.pool.DataSource baseDs = new org.apache.tomcat.jdbc.pool.DataSource();
            baseDs.setDriverClassName(driveClass);
            //2、url，用户名，密码
            baseDs.setUrl(url);
            baseDs.setUsername(mysqlUserName);
            baseDs.setPassword(mysqlPassWord);
            //3、初始化连接大小
            baseDs.setInitialSize(5);
            //4、连接池最大数据量
            //dataSource.setMaxTotal(500);
            //5、连接池最大小空闲
            baseDs.setMinIdle(1);
            baseDs.setMaxIdle(20);
            //6、最大等待时间 单位毫秒
            //dataSource.setMaxWaitMillis(20 * 1000);
            //7、指明连接是否被空闲连接回收器(如果有)进行检验
            //dataSource.setPoolPreparedStatements(true);
            //8、运行一次空闲连接回收器的时间间隔（60秒）
            baseDs.setTimeBetweenEvictionRunsMillis(30 * 1000);
            //9、验证时使用的SQL语句
            baseDs.setValidationQuery("SELECT 1");
            //10、借出连接时不要测试，否则很影响性能
            //11、申请连接的时候检测，如果空闲时间大于  timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
            baseDs.setTestWhileIdle(true);
            baseDs.setTestOnBorrow(true);
            baseDs.setTestOnConnect(true);
            baseDs.setTestOnReturn(false);
            jdbcTemplate.setDataSource(baseDs);

            log.info("BaseCase DataBase的Num活跃数{}, 空闲数{}", baseDs.getNumActive(), baseDs.getNumIdle());
            log.info("BaseCase DataBase的活跃数{}, 空闲数{}", baseDs.getActive(), baseDs.getIdle());
            }catch (Exception e){
            log.info("连接数据库失败！");
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void RedisLink() {
        try {
            Properties saoBing = new Properties();

            InputStream saoBingIns = BaseCase.class.getResourceAsStream("/saoBingRedis.properties");

            saoBing.load(saoBingIns);

            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(saoBing.getProperty("RedisHostName"));
            redisStandaloneConfiguration.setPassword(saoBing.getProperty("RedisPassWord"));
            redisStandaloneConfiguration.setPort(Integer.parseInt(saoBing.getProperty("RedisPort")));
            redisStandaloneConfiguration.setDatabase(Integer.parseInt(saoBing.getProperty("RedisDataBase")));

            JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);

            saoBingRedisTemplate.setConnectionFactory(connectionFactory);
            //先调用afterPropertiesSet方法,此方法是应该是初始化参数和初始化工作。
            saoBingRedisTemplate.afterPropertiesSet();
            log.info("链接redis{}成功", saoBing.getProperty("HostName"));
            saoBingIns.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("链接redis{}失败");
        }
    }

    @AfterSuite
    public void RedisLinkClose() {
        try {
            saoBingRedisTemplate.getConnectionFactory().getConnection().close();
            log.info("关闭redis链接成功");
        }catch (Exception e) {
            log.info("关闭redis链接失败");
        }
    }

//    public static void main(String[] args) {
//        RedisLink();
//        saoBingRedisTemplate.opsForZSet().add("lixin", "16_moon", 100);
//        RedisLinkClose();
//    }

    /**
     * 准备工作(cookie,token,sql等)
     *
     * @param testCase
     * @return
     */
    public void initTestCase(TestCase testCase) throws Exception {
        // 如果SQL语句存在，就执行SQL语句
        if (!StringUtils.isNullOrEmpty(testCase.getMysql())) {
            log.info("执行sql语句{}", testCase.getMysql());
            try {
                operationMysql(testCase.getMysql());
            }catch (Exception e){
                e.printStackTrace();
                log.info("执行sql语句失败，{}", testCase.getMysql());
            }
        }

        //ToDo mongodb相关操作
//        if (!testCase.getMongodb().isEmpty()) {//判断是否要执行mongo文件
//            //暂时未处理
//            operationMongoSql(testCase.getMongoDB(),testCase.getPlatInfo());
//        }

        //先用一个Map来保存已存在的redis的host信息
        HashMap<String, String> redisMap = new HashMap<>();
        redisMap.put("10.10.156.33", "1");

        //如果redis语句存在，就执行redis语句
        if (!StringUtils.isNullOrEmpty(testCase.getRedis())) {
            String[] redisArray = testCase.getRedis().split(";");
            //判断是否需要连接其他redis
            if (!StringUtils.isNullOrEmpty(testCase.getReserved().trim())) {
                if (redisMap.containsKey(testCase.getReserved().trim())){
                    log.info("在{},redis中执行语句", testCase.getReserved());
                    operationRedisOnNew(redisArray, redisMap.get(testCase.getReserved().trim()));
                }else {
                    log.info("不存在对应的redis信息，请配置对应的redis数据源");
                }
            }else {
                operationRedis(redisArray);
            }
        }
    }

    /**
     * 执行用例后对m数据库和redis进行操作
     * @param testCase
     */
    public void afterRunTestCase(TestCase testCase){
        if (!StringUtils.isNullOrEmpty(testCase.getClearMysql())) {
            log.info("执行sql语句{}", testCase.getClearMysql());
            try {
                operationMysql(testCase.getClearMysql());
            }catch (Exception e){
                e.printStackTrace();
                log.info("执行sql语句失败，{}", testCase.getMysql());
            }
        }

        //先用一个Map来保存已存在的redis的host信息
        HashMap<String, String> redisMap = new HashMap<>();
        redisMap.put("10.10.156.33", "1");

        //如果redis语句存在，就执行redis语句
        if (!StringUtils.isNullOrEmpty(testCase.getClearRedis())) {
            String[] redisArray = testCase.getClearRedis().split(";");
            //判断是否需要连接其他redis
            if (!StringUtils.isNullOrEmpty(testCase.getReserved().trim())) {
                if (redisMap.containsKey(testCase.getReserved().trim())){
                    log.info("在{},redis中执行语句", testCase.getReserved());
                    operationRedisOnNew(redisArray, redisMap.get(testCase.getReserved().trim()));
                }else {
                    log.info("不存在对应的redis信息，请配置对应的redis数据源");
                }
            }else {
                operationRedis(redisArray);
            }
        }
    }

    public void operationMysql(String sqlStr) throws SQLException {
        String[] sqlArr = sqlStr.split(";");
        for(int i = 0; i < sqlArr.length; i++) {
            try {
                jdbcTemplate.execute(sqlArr[i]);
                log.info("执行请求前开始第{}条执行sql：{}", i ,sqlArr[i]);
            }catch (Exception e){
                e.printStackTrace();
                log.info("执行请求前执行第{}条sql：{}出错", i , sqlArr[i]);
            }finally {
                jdbcTemplate.getDataSource().getConnection().close();
            }
        }
    }

    /**
     *
     * 清理redis 支持set和del和rename
     */
    public void operationRedis(String[] redisArray) {
        try {
            for (String str : redisArray) {
                if (str.trim().toLowerCase().startsWith("set")) {//修改key
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    RedisUtils.setRedisValueByKeyForever(key, value);
                    log.info("redis 操作 set成功，key = {}, value = {}", key, value);
                }
                if (str.trim().toLowerCase().startsWith("del")) {//删除key
                    String key = str.trim().split(" ")[1];
                    RedisUtils.delRedisByKey(key);
                    log.info("redis 操作 del成功， key = {}", key);
                }
                if (str.trim().toLowerCase().startsWith("rename")) {//rename key
                    if (!RedisUtils.exists(str.trim().split(" ")[1])) {
                        log.info("redis 的rename 操作时，对应的oldKey：{}不存在", str.trim().split(" ")[1]);
                    }else {
                        String oldKey = str.trim().split(" ")[1];
                        String newKey = str.trim().split(" ")[1];
                        RedisUtils.renameOldKey(oldKey, newKey);
                        log.info("redis 操作 rename成功， oldKey = {}，newKey = {} ", oldKey, newKey);
                    }
                }
                if (str.trim().toLowerCase().startsWith("hset")) {//HSET KEY_NAME FIELD VALUE
                    String key = str.trim().split(" ")[1];
                    String field = str.trim().split(" ")[2];
                    String value = str.trim().split(" ")[3];
                    RedisUtils.hsetKeyValue(key, field, value);
                    log.info("在新redis: 操作 hset成功， key = {}，field = {}，value = {}", key, field, value);
                }
                if (str.trim().toLowerCase().startsWith("zadd")) {//zadd myZSet 1 zlh   ---添加分数为1，值为zlh的zset集合
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[3];
                    Double score = Double.parseDouble(str.trim().split(" ")[2]);
                    RedisUtils.zaddKeyValue(key, value, score);
                    log.info("在新redis: 操作 zadd， key = {}，value = {}，score = {}", key, value, score);
                }
                if (str.trim().toLowerCase().startsWith("zrem")) {//zrem myZSet zlh   --删除值为zlh的zset成员
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    RedisUtils.zremValueInZSet(key, value);
                    log.info("在新redis: 操作 zrem， key = {}，value = {}", key, value);
                }
                if (str.trim().toLowerCase().startsWith("lpop")) {//Lpop KEY_NAME 用于移除并返回列表的第一个元素。
                    String key = str.trim().split(" ")[1];
                    RedisUtils.leftPopKey(key);
                    log.info("在新redis: 操作 lpop， key = {}", key);
                }
                if (str.trim().toLowerCase().startsWith("rpop")) {//Rpop KEY_NAME 用于移除并返回列表的最后一个元素。
                    String key = str.trim().split(" ")[1];
                    RedisUtils.rightPopKey(key);
                    log.info("在新redis: 操作 rpop， key = {}", key);
                }
                if (str.trim().toLowerCase().startsWith("lpush")) {//Lpush KEY_NAME value 将一个或多个值插入到列表头部
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    RedisUtils.leftPushKeyValue(key, value);
                    log.info("在新redis: 操作 lpush， key = {}, value = {}", key, value);
                }
                if (str.trim().toLowerCase().startsWith("rpush")) {//Rpush KEY_NAME 在列表中添加一个或多个值
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    RedisUtils.rightPushKeyValue(key, value);
                    log.info("在新redis: 操作 rpush， key = {}, value = {}", key, value);
                }
                if (str.trim().toLowerCase().startsWith("lset")) {//LSET key index value  通过索引设置列表元素的值。
                    String key = str.trim().split(" ")[1];
                    Long index = Long.parseLong(str.trim().split(" ")[2]);
                    String value = str.trim().split(" ")[3];
                    RedisUtils.leftSetValueByIndex(key, value, index);
                    log.info("在新redis: 操作 lset， key = {}, value = {}, index = {}", key, value, index);
                }
            }//for
        }catch (Exception e) {
            log.error("\n❤执行REDIS语句报错:{}", e);
        }
    }

    /**
     * 在指定的redis中进行set和del和rename操作
     * @param redisArray
     * @param code
     */
    private void operationRedisOnNew(String[] redisArray, String code) {
        try {
            for (String str : redisArray) {
                if (str.trim().toLowerCase().startsWith("set")) {//修改key
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    if (code.equals("1")){
                        saoBingRedisTemplate.opsForValue().set(key, value);
                        log.info("在新redis: 操作 set成功 key = {}, value = {}", key, value);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("del")) {//删除key
                    String key = str.trim().split(" ")[1];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.delete(key);
                        log.info("在新redis: 操作 del成功， key = {}", key);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("rename")) {//rename key
                    if (!RedisUtils.exists(str.trim().split(" ")[1])) {
                        log.info("redis 的rename 操作时，对应的oldKey：{}不存在", str.trim().split(" ")[1]);
                    }else {
                        String oldKey = str.trim().split(" ")[1];
                        String newKey = str.trim().split(" ")[2];
                        if (code.equals("1")) {
                            saoBingRedisTemplate.rename(oldKey, newKey);
                            log.info("在新redis: 操作 rename成功， oldKey = {}，newKey = {}", oldKey, newKey);
                        }else {
                            log.info("不存在对应的redis信息");
                        }
                    }
                }
                if (str.trim().toLowerCase().startsWith("hset")) {//HSET KEY_NAME FIELD VALUE
                    String key = str.trim().split(" ")[1];
                    String field = str.trim().split(" ")[2];
                    String value = str.trim().split(" ")[3];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForHash().put(key, field, value);
                        log.info("在新redis: 操作 hset成功， key = {}，field = {}，value = {}", key, field, value);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("zadd")) {//zadd myZSet 1 zlh   ---添加分数为1，值为zlh的zset集合
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[3];
                    Double score = Double.parseDouble(str.trim().split(" ")[2]);
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForZSet().add(key, value, score);
                        log.info("在新redis: 操作 zadd， key = {}，value = {}", key, value);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("zrem")) {//zrem myZSet zlh   --删除值为zlh的zset成员
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForZSet().remove(key, value);
                        log.info("在新redis: 操作 zrem， key = {}，value = {}", key, value);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("lpop")) {//Lpop KEY_NAME 用于移除并返回列表的第一个元素。
                    String key = str.trim().split(" ")[1];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForList().leftPop(key);
                        log.info("在新redis: 操作 lpop， key = {}", key);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("rpop")) {//Rpop KEY_NAME 用于移除并返回列表的最后一个元素。
                    String key = str.trim().split(" ")[1];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForList().rightPop(key);
                        log.info("在新redis: 操作 rpop， key = {}", key);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("lpush")) {//Lpush KEY_NAME value 将一个或多个值插入到列表头部
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForList().leftPush(key, value);
                        log.info("在新redis: 操作 lpush， key = {}, value = {}", key, value);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("rpush")) {//Rpush KEY_NAME 在列表中添加一个或多个值
                    String key = str.trim().split(" ")[1];
                    String value = str.trim().split(" ")[2];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForList().rightPush(key, value);
                        log.info("在新redis: 操作 rpush， key = {}, value = {}", key, value);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
                if (str.trim().toLowerCase().startsWith("lset")) {//LSET key index value  通过索引设置列表元素的值。
                    String key = str.trim().split(" ")[1];
                    Long index = Long.parseLong(str.trim().split(" ")[2]);
                    String value = str.trim().split(" ")[3];
                    if (code.equals("1")) {
                        saoBingRedisTemplate.opsForList().set(key, index, value);
                        log.info("在新redis: 操作 lset， key = {}, value = {}, index = {}", key, value, index);
                    }else {
                        log.info("不存在对应的redis信息");
                    }
                }
            }//for
        }catch (Exception e) {
            log.error("\n❤在新redis执行语句报错:{}", e);
        }
    }

    /**
     * 校验接口返回值
     * @param response
     * @param expectResponse
     */
    public void verifyResponse(String response, String expectResponse) {
        AssertUtil.flag = true;
        AssertUtil.errors.clear();

        if ("error".equals(response)){
            AssertUtil.flag = false;
        }

        if (StringUtils.isNullOrEmpty(expectResponse)) {
            AssertUtil.expectResponseIsNullOrEmpty();
        }

        if (expectResponse.trim().startsWith("contains+")) {
            AssertUtil.contains(expectResponse.substring(9), response);
        }

        if (!isJson(response) && !expectResponse.startsWith("contains+")){
            AssertUtil.equals(expectResponse, response);
        }else {
                JSONObject jsonObject = JSON.parseObject(response);
                //1.过滤期望值中包含"http://"的校验
                expectResponse = expectResponse.replace("http://","###");
                Map<String, String> map = str2map(expectResponse, ";", ":");
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String expectKey = entry.getKey().trim();
                    String expectValue = entry.getValue().trim();

                    //2.替换回"http://"
                    expectValue=expectValue.replace("###","http://");
                    // 检查整个返回是否包含目标字段
                    if (!expectKey.startsWith("$") && ("contain").equals(expectKey)) {

                        AssertUtil.contains(expectValue, response);
                    } else if (!expectKey.startsWith("$")) {
                        ReportUtil.log("用例参数有误: 请检查 [ contain ] 参数拼写是否有误");
                    }

                    try {
                        if (expectKey.startsWith("$")) {
                            if(JSONPath.eval(jsonObject, expectKey)!=null) {
                                String actualValue = JSONPath.eval(jsonObject, expectKey).toString();
                                AssertUtil.equals(expectKey, expectValue, actualValue);
                            } else {//如果返回Response Body: {"code":500,"message":"Internal Server Error","bean":null}，解析bean为null对象并非字符串
                                AssertUtil.equals(expectKey, expectValue, "null");
                            }
                        }
                    } catch (Exception e) {
                        AssertUtil.flag = false;
                        ReportUtil.log("返回JSON数据没有包含 [ " + expectKey + " ]字段, 严重错误");
                    }
                }
        }

        if (AssertUtil.flag) {
            ReportUtil.log("❤❤❤❤❤❤❤❤测试用例成功❤❤❤❤❤❤❤❤: Response check Pass");
        } else {
            ReportUtil.log("❤❤❤❤❤❤❤❤测试用例失败❤❤❤❤❤❤❤❤: Response check Fail");
        }

        Assert.assertTrue(AssertUtil.flag, "仅做判断所有检查是否通过。如果出现该信息，请看前面输出的校验信息");
    }

    /**
     * 判断字符串是否为Json
     *
     * @param content
     * @return boolen
     */
    private boolean isJson(String content) {
        try {
            JSONObject jsonStr = JSON.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 字符串键值对转成map
     *
     * @param paramters
     * @param splitChar1 一级分隔符，将字符串分成多个键值对key-value
     * @param splitChar2 二级分隔符，分割key和value
     * @return
     */
    private Map<String, String> str2map(String paramters, String splitChar1, String splitChar2) {
        if (paramters.isEmpty()) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        String[] paramArray = paramters.split(splitChar1);
        for (String param : paramArray) {
            String[] array = param.split(splitChar2);
            map.put(array[0], array[1]);
        }
        return map;
    }
}
