package com.iqianjin.test.teststage.testng;

import com.iqianjin.autotest.springboot.listener.TestngRetry;
import com.iqianjin.test.teststage.service.Imp.AsyncServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

@Slf4j
public class TestListenerAdapterExt extends TestListenerAdapter {

    public static JdbcTemplate jdbcTemplate = new JdbcTemplate();

    //    @Value("${spring.datasource.teststage.url}")
    private  String url = "jdbc:mysql://10.10.186.131:3306/test_stage?useUnicode=true&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";

    //    @Value("${spring.datasource.teststage.username}")
    private String username = "root";

    //    @Value("${spring.datasource.teststage.password}")
    private String password = "iqianjin@123";

    private String driverClassName = "com.mysql.jdbc.Driver";

    {
        try {
//            DataSource baseDs = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).build();
            org.apache.tomcat.jdbc.pool.DataSource baseDs = new org.apache.tomcat.jdbc.pool.DataSource();
            baseDs.setDriverClassName(driverClassName);
            //2、url，用户名，密码
            baseDs.setUrl(url);
            baseDs.setUsername(username);
            baseDs.setPassword(password);
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
            log.info("TestListenerAdapterExt DataBase的Num活跃数{}, 空闲数{}", baseDs.getNumActive(), baseDs.getNumIdle());
            log.info("TestListenerAdapterExt DataBase的活跃数{}, 空闲数{}", baseDs.getActive(), baseDs.getIdle());
        }catch (Exception e){
            log.info("记录成功或失败数量时，连接数据库失败！");
            e.printStackTrace();
        }
    }
    public void onTestSuccess(ITestResult tr) {
        // TODO Auto-generated method stub
        try {
            saveSuccessResult(tr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TestngRetry.resetRetryCount();
    }

    public void onTestFailure(ITestResult tr) {
//        TestngRetry.
        try {
            saveFailResult(tr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //saveResult(tr);
        super.onTestFailure(tr);
    }

    public void onTestSkipped(ITestResult tr) {
        saveResult(tr);
        super.onTestSkipped(tr);
    }

    private void saveResult(ITestResult tr) {
        Throwable throwable = tr.getThrowable();
        if (null == throwable) {
            return;
        }
    }

    private void saveSuccessResult(ITestResult tr) throws SQLException {
        String sql = "update test_case set succ_count = succ_count + 1 where id = " + tr.getTestContext().getAttribute("RecordId");
        log.info("记录成功的sql为{}", sql);
        try {
            jdbcTemplate.execute(sql);
        }catch (Exception e){
            log.info("最后记录成功次数时失败！");
            e.printStackTrace();
        }finally {
            log.info("开始关闭数据库连接");
            jdbcTemplate.getDataSource().getConnection().close();
        }

        if(tr.getTestContext().getAttribute("RecordId")!=null
                && AsyncServiceImpl.threadLocalSuccCount.get()!=null
                && AsyncServiceImpl.threadLocalSuccCount.get().equals("single")) {
            String getSuccCountSql = "select succ_count from test_case where id = " + tr.getTestContext().getAttribute("RecordId");
            String succCount = jdbcTemplate.queryForObject(getSuccCountSql, String.class);
            //if(AsyncServiceImpl.threadLocalSuccCount.get().equals("single"))
            AsyncServiceImpl.threadLocalSuccCount.set(succCount);
//            if (AsyncServiceImpl.FAIL_FLAG.get() != "1"){
//                AsyncServiceImpl.FAIL_FLAG.set("0");
//            }
            log.info("AsyncServiceImpl.SUCCESS_COUNT", AsyncServiceImpl.SUCCESS_COUNT.get());
            log.info("succCount:" + succCount);
        }
        log.info("RecordId:" + tr.getTestContext().getAttribute("RecordId"));
        log.info("this time success,success_count add 1 " + tr.getInstanceName());
    }

    private void saveFailResult(ITestResult tr) throws SQLException {
        String sql = "update test_case set fail_count = fail_count + 1 where id = " + tr.getTestContext().getAttribute("RecordId");
        log.info("记录失败的sql为{}", sql);

        try {
            jdbcTemplate.execute(sql);
        }catch (Exception e){
            log.info("最后记录失败次数时失败！");
            e.printStackTrace();
        }finally {
            log.info("开始关闭数据库连接");
            jdbcTemplate.getDataSource().getConnection().close();
        }

        if(tr.getTestContext().getAttribute("RecordId")!=null
                && AsyncServiceImpl.threadLocalFailCount.get()!=null
                && AsyncServiceImpl.threadLocalFailCount.get().equals("single")) {
            String getFailCountSql = "select fail_count from test_case where id = " + tr.getTestContext().getAttribute("RecordId");
            String failCount = jdbcTemplate.queryForObject(getFailCountSql, String.class);
            AsyncServiceImpl.threadLocalFailCount.set(failCount);
//            AsyncServiceImpl.FAIL_FLAG.set("1");
            log.info("AsyncServiceImpl.FAIL_COUNT", AsyncServiceImpl.FAIL_COUNT.get());
            log.info("failCount:{}", failCount);
        }
        log.info("RecordId:" + tr.getTestContext().getAttribute("RecordId"));
        log.info("this time fail,fail_count add 1 "+tr.getInstanceName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        BigDecimal one = new BigDecimal("1");
        //开始统计成功和失败数量还有成功率
        Integer successCount = testContext.getPassedTests().size();
        Integer failCount = testContext.getFailedTests().size();
//        Double successRate =  new BigDecimal((float)successCount / (float)(successCount + failCount)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double successRate =  new BigDecimal((float)successCount / (float)(successCount + failCount)).divide(one,4, BigDecimal.ROUND_HALF_UP).doubleValue();
        AsyncServiceImpl.SUCCESS_COUNT.set(successCount);
        AsyncServiceImpl.FAIL_COUNT.set(failCount);
//        AsyncServiceImpl.SUCCESS_RATE.set(successRate * 100 + "%");
        AsyncServiceImpl.SUCCESS_RATE.set(Double.parseDouble(String.format("%.2f", successRate * 100)));

        // List of test results which we will delete later
        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
        // collect all id's from passed test
        Set<Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests()
                .getAllResults()) {
            // logger.info("PassedTests = " + passedTest.getName());
            passedTestIds.add(getId(passedTest));
        }

        Set<Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests()
                .getAllResults()) {
            // logger.info("failedTest = " + failedTest.getName());
            // id = class + method + dataprovider
            int failedTestId = getId(failedTest);

            // if we saw this test as a failed test before we mark as to be
            // deleted
            // or delete this failed test if there is at least one passed
            // version
            if (failedTestIds.contains(failedTestId)
                    || passedTestIds.contains(failedTestId)) {
                testsToBeRemoved.add(failedTest);
            } else {
                failedTestIds.add(failedTestId);
            }
        }

        // finally delete all tests that are marked
        for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext();) {
            ITestResult testResult = iterator.next();

            if (testsToBeRemoved.contains(testResult)) {
                iterator.remove();
            }
        }
    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
        return id;
    }
}
