package com.iqianjin.test.teststage.testng;

import com.iqianjin.autotest.springboot.utils.StringUtil;
import org.testng.Reporter;

public class ReportUtil {
    private static String reportName = "爱钱进自动化测试报告";

    private static String splitTimeAndMsg = "===";

    public static void log(String msg) {
        Reporter.log(msg, true);
    }

    public static String getReportName() {
        return reportName;
    }

    public static String getSpiltTimeAndMsg() {
        return splitTimeAndMsg;
    }

    public static void setReportName(String reportName) {
        if(StringUtil.isNotEmpty(reportName)){
            ReportUtil.reportName = reportName;
        }
    }
}

