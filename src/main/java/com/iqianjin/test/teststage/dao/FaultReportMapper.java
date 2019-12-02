package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.FaultReport;

public interface FaultReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FaultReport record);

    int insertSelective(FaultReport record);

    FaultReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FaultReport record);

    int updateByPrimaryKey(FaultReport record);
}