package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.entity.PerformanceTestReport;

import java.util.List;

/**
 * @author lixin
 * @creat 2019-08-13 下午06:29
 */
public interface PerformanceTestReportManager {
    void savePerformanceTestReport(PerformanceTestReport performanceTestReport);

    List<PerformanceTestReport> findReportByPlatFormAndVersion(Integer platForm, String version);

    List<PerformanceTestReport> findReportByPlatForm(Integer platForm);

    List<PerformanceTestReport> findReportByVersion(String version);

    List<PerformanceTestReport> findAll();

    void delReportById(Integer id);

    PerformanceTestReport findReportById(Integer id);

    void updateReport(PerformanceTestReport performanceTestReport);
}
