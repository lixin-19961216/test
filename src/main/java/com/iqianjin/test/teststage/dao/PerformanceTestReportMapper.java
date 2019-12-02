package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerformanceTestReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PerformanceTestReport record);

    int insertSelective(PerformanceTestReport record);

    PerformanceTestReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PerformanceTestReport record);

    int updateByPrimaryKey(PerformanceTestReport record);

    List<PerformanceTestReport> findReportByPlatFormAndVersion(@Param("platForm") Integer platForm, @Param("version") String version);

    List<PerformanceTestReport> findReportByPlatForm(Integer platForm);

    List<PerformanceTestReport> findReportByVersion(String version);

    List<PerformanceTestReport> findAll();
}