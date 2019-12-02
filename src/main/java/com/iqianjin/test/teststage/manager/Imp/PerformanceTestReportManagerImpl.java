package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.PerformanceTestReportMapper;
import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import com.iqianjin.test.teststage.manager.PerformanceTestReportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PerformanceTestReportManagerImpl implements PerformanceTestReportManager {

    @Autowired
    PerformanceTestReportMapper performanceTestReportMapper;

    @Override
    public void savePerformanceTestReport(PerformanceTestReport performanceTestReport) {
        performanceTestReportMapper.insert(performanceTestReport);
    }

    @Override
    public List<PerformanceTestReport> findReportByPlatFormAndVersion(Integer platForm, String version) {
        return performanceTestReportMapper.findReportByPlatFormAndVersion(platForm, version);
    }

    @Override
    public List<PerformanceTestReport> findReportByPlatForm(Integer platForm) {
        return performanceTestReportMapper.findReportByPlatForm(platForm);
    }

    @Override
    public List<PerformanceTestReport> findReportByVersion(String version) {
        return performanceTestReportMapper.findReportByVersion(version);
    }

    @Override
    public List<PerformanceTestReport> findAll() {
        return performanceTestReportMapper.findAll();
    }

    @Override
    public void delReportById(Integer id) {
        performanceTestReportMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PerformanceTestReport findReportById(Integer id) {
        return performanceTestReportMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateReport(PerformanceTestReport performanceTestReport) {
        performanceTestReportMapper.updateByPrimaryKey(performanceTestReport);
    }
}
