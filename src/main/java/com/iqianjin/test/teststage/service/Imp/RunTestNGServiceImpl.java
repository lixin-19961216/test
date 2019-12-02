package com.iqianjin.test.teststage.service.Imp;

//import com.iqianjin.autotest.springboot.listener.ExtentTestNGIReporterListener;
import com.iqianjin.test.teststage.testng.ExtentTestNGIReporterListener;
import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.entity.TaskExeHistory;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.manager.TaskExeHistoryManager;
import com.iqianjin.test.teststage.manager.TaskRecordManager;
import com.iqianjin.test.teststage.manager.TestCaseManager;
import com.iqianjin.test.teststage.service.RunTestNGService;
import com.iqianjin.test.teststage.utils.TransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testng.TestNG;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class RunTestNGServiceImpl implements RunTestNGService {
    @Value("${spring.testCaseReport.dir}")
    private String testCaseReportDir;

    @Value("${spring.testCaseReportRealIndex.dir}")
    private  String testCaseReportRealDir;

    @Value("${spring.domain.name}")
    private String domain;

    @Value("${spring.testng.dir}")
    private String testNGDir;

    public static String runDomain;

    public static Object[][] dataProvider;

    @Autowired
    private TaskExeHistoryManager taskExeHistoryManager;

    @Autowired
    private TaskRecordManager taskRecordManager;

    @Autowired
    private TestCaseManager testCaseManager;

    @Override
    public String generateTestNGReport(Integer taskRecordId, ServerInfo serverInfo) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = dateformat.format(System.currentTimeMillis())+ ThreadLocalRandom.current().nextInt(100000);

        try {
            ExtentTestNGIReporterListener.OUTPUT_FOLDER = testCaseReportDir + fileName + "/";
            File test = new File(ExtentTestNGIReporterListener.OUTPUT_FOLDER);
            if(!test.exists()) {
                log.info("ExtentTestNGIReporterListener.OUTPUT_FOLDER not exists");
                test.mkdirs();
                log.info("创建ExtentTestNGIReporterListener的OUTPUT_FOLDER目录成功，目录为：{}", ExtentTestNGIReporterListener.OUTPUT_FOLDER);
            }
            //生成object[][],为Test做准备
            String runTestCaseIds = (taskRecordManager.findTestCaseIdsById(taskRecordId));
            runDomain = (serverInfo.getApiUrl());
            List<Integer> integerList = TransformUtil.str2list(runTestCaseIds);
            int m = integerList.size();
            dataProvider = new Object[m][1];
            for (int i= 0; i < m; i++){
                TestCase testCase = testCaseManager.findTestCaseById(integerList.get(i));
                dataProvider[i][0] = testCase;
            }

            log.info("运行的接口用例id为{}", runTestCaseIds);
            log.info("运行时的域名为{}", runDomain);

            TestNG testNG = new TestNG();
            List<String> suites = new ArrayList<String>();

            suites.add(testNGDir);//添加TestNG.xml配置文件

            testNG.setTestSuites(suites);//添加测试集
            testNG.setOutputDirectory(ExtentTestNGIReporterListener.OUTPUT_FOLDER);//添加测试后的报告输出路径
            testNG.run();

            //把报告迁移到指定的目录下面，然后用nginx进行转发，得以访问。
            File source = new File(ExtentTestNGIReporterListener.OUTPUT_FOLDER + "index.html");
            (new File(testCaseReportRealDir + fileName)).mkdir();
            File target = new File(testCaseReportRealDir + fileName + "/index.html");
            try {
                FileUtils.copyFile(source, target);
                log.info("开始迁移，原始路径{},目的路径{}", source.getAbsolutePath(), target.getAbsolutePath());
            }catch (Exception e){
                log.info("迁移testng报告出错,原始路径{},目的路径{}", source.getAbsolutePath(), target.getAbsolutePath());
                e.printStackTrace();
                return "迁移testng报告出错fail";
            }

            //保存运行记录
            TaskExeHistory taskExeHistory = new TaskExeHistory();
            taskExeHistory.setTaskId(taskRecordId);
            taskExeHistory.setBuildNo(fileName);
            taskExeHistory.setReportUrl(domain + fileName + "/index.html");
            taskExeHistory.setCreateTime(new Date());
            taskExeHistory.setUpdateTime(new Date());
            taskExeHistoryManager.addTaskExeHistory(taskExeHistory);

            return fileName;
        }catch (Exception e){
            log.info("exception occurs:" + e.getMessage());
            e.printStackTrace();
            return "fail";
        }
    }
}
