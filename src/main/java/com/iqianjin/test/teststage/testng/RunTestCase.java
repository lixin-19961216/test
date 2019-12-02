package com.iqianjin.test.teststage.testng;

import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.service.Imp.RunTestNGServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class RunTestCase extends BaseCase {
    /**
     *
     * @param testCase
     */
    @Test(description = "执行每条case，进行测试", dataProvider = "data")
    public void runTestCase(ITestContext context, TestCase testCase){
        context.setAttribute("RecordId", testCase.getId());
        log.info("\n❤❤❤❤❤❤❤❤执行测试用例❤❤❤❤❤❤❤❤: ["
                + testCase.getCaseNo() + "] 描述: " + testCase.getDescription());
        this.runOneTestCase(testCase);
    }

    /**
     * DataProvider,可看做Test运行时的数据仓库，为一个对象数组
     * @return
     */
    @DataProvider(name = "data")
    public Object[][] getData(){
        Object[][] result = RunTestNGServiceImpl.dataProvider;
        Assert.assertNotNull(result, "待执行的测试用例数为0,请检查入参");
        return result;
    }

    //执行每一个测试用例
    private void runOneTestCase(TestCase testCase) {
        try {
            //1，拼接url，url为域名+路径
            String domain = RunTestNGServiceImpl.runDomain;
            String api = testCase.getApiInfo();
            String url = domain + api;
            log.info("开始拼接url，拼接后的url为：{}", url);

            //2.准备工作，判断testCase中是否需要向数据库或者redis执行操作。
            initTestCase(testCase);

            //3.获取请求参数
            String parameters = testCase.getParams();

            // 4.判断请求方式，拼接参数并发送请求
            String method = testCase.getMethod().toLowerCase();
            String response = "";
            switch (method) {
                case "get":
                    log.info("Method       : GET");
                    log.info("Request URL  : " + url);
                    log.info("Request Param: " + parameters);

                    url = parameters.isEmpty() ? url : url + "?" + parameters;
                    response = httpClientUtil.sendHttpGet(url, testCase);
                    log.info("get请求的response为：{}", response);
                    break;
                case "post":
                    log.info("Method       : POST");
                    log.info("Request  URL : " + url);
                    log.info("Request  Body: " + parameters);
                    response = httpClientUtil.sendHttpPost(url, testCase);
                    log.info("post请求的response为：{}", response);
                    break;
                default:
                    log.info("暂时不支持get和post方式以外的请求");
                    return;
            }
            // 5.校验请求返回数据
            verifyResponse(response, testCase.getExpectResponse());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            afterRunTestCase(testCase);
        }
    }
}
