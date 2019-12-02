package com.iqianjin.test.teststage.entity;

public class TestCase {
    private Integer id;

    /**
     * 包括app,web,h5
     */
    private String platInfo;

    /**
     * 功能模块
     */
    private String featureModule;

    /**
     * 用例编号
     */
    private String caseNo;

    private String env;

    /**
     * 用例描述信息
     */
    private String description;

    /**
     * 具体api接口
     */
    private String apiInfo;

    private String headers;

    /**
     * 请求接口方式
     */
    private String method;

    /**
     * 参数，json格式直接传入，普通格式，各参数key=value用&连接，例如a=1111&b=2222&c=3333，如果没有参数，可以为空
     */
    private String params;

    /**
     * 测试用户，指定使用某个测试账号运行用例，例如填user2，在配置文件中必须配置user2.userId和user2.nickName，一一对应，如果为空，默认读配置文件中的user1（20171123新增
     */
    private String userInfo;

    /**
     * 如果接口需要带cookie，填Y；如果不需要，为空即可
     */
    private String cookie;

    /**
     * 同Cookie，需要填Y；不需要，为空即可
     */
    private String token;

    /**
     * 接口执行前需要在数据库造数据，将sql文件名填入即可，例如loan.sql，文件存放在测试工程的testdata目录；如果不需要，为空即可
     */
    private String mysql;

    /**
     * 同mysql，填文件名即可。（由于时间原因，该功能暂时未做，如有需要，后续再做）
     */
    private String mongodb;

    /**
     * 通mysql，格式：set key value;del key（多个语句以;隔开，仅支持set和del）
     */
    private String redis;

    /**
     * 接口调用成功后，验证数据库
     */
    private String expectMysql;

    /**
     * 接口调用成功后，验证返回值，格式为：需验证字段的jsonpath:期望值,需验证字段的jsonpath2:期望值2...，多个验证点以逗号隔开；如果不需验证，为空即可
     */
    private String expectResponse;

    /**
     * 测试完毕后，清空数据库中测试生成的数据，格式为：sql语句,sql语句... ，多个语句以逗号隔开；如果不需要清除，为空即可
     */
    private String clearMysql;

    /**
     * 清除Redis的key，格式为：del key1;del key2...，多个语句以逗号隔开；如果不需要清除，为空即可（20171123新增）
     */
    private String clearRedis;

    /**
     * 标志位，0代表未删除；1代表已删除，前端不展示；
     */
    private Integer flag;

    /**
     * 用例适用的分支名称
     */
    private String branch;

    /**
     * case的等级，P0，P1，P2等等
     */
    private String caseLevel;

    private Integer failCount;

    /**
     * 预留位——现在作为连接另外redis的参数
     */
    private String reserved;

    private Integer succCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatInfo() {
        return platInfo;
    }

    public void setPlatInfo(String platInfo) {
        this.platInfo = platInfo == null ? null : platInfo.trim();
    }

    public String getFeatureModule() {
        return featureModule;
    }

    public void setFeatureModule(String featureModule) {
        this.featureModule = featureModule == null ? null : featureModule.trim();
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo == null ? null : caseNo.trim();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(String apiInfo) {
        this.apiInfo = apiInfo == null ? null : apiInfo.trim();
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers == null ? null : headers.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo == null ? null : userInfo.trim();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie == null ? null : cookie.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getMysql() {
        return mysql;
    }

    public void setMysql(String mysql) {
        this.mysql = mysql == null ? null : mysql.trim();
    }

    public String getMongodb() {
        return mongodb;
    }

    public void setMongodb(String mongodb) {
        this.mongodb = mongodb == null ? null : mongodb.trim();
    }

    public String getRedis() {
        return redis;
    }

    public void setRedis(String redis) {
        this.redis = redis == null ? null : redis.trim();
    }

    public String getExpectMysql() {
        return expectMysql;
    }

    public void setExpectMysql(String expectMysql) {
        this.expectMysql = expectMysql == null ? null : expectMysql.trim();
    }

    public String getExpectResponse() {
        return expectResponse;
    }

    public void setExpectResponse(String expectResponse) {
        this.expectResponse = expectResponse == null ? null : expectResponse.trim();
    }

    public String getClearMysql() {
        return clearMysql;
    }

    public void setClearMysql(String clearMysql) {
        this.clearMysql = clearMysql == null ? null : clearMysql.trim();
    }

    public String getClearRedis() {
        return clearRedis;
    }

    public void setClearRedis(String clearRedis) {
        this.clearRedis = clearRedis == null ? null : clearRedis.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(String caseLevel) {
        this.caseLevel = caseLevel == null ? null : caseLevel.trim();
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved == null ? null : reserved.trim();
    }

    public Integer getSuccCount() {
        return succCount;
    }

    public void setSuccCount(Integer succCount) {
        this.succCount = succCount;
    }

    public String toString() {
        return String.format("[%s], Description:%s, Method:%s, Url:%s, Param:%s", this.caseNo, this.description, this.method, this.apiInfo, this.params);
    }
}
