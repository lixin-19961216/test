package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.BusinessCase;

import java.util.List;

public interface BusinessCaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessCase record);

    int insertSelective(BusinessCase record);

    BusinessCase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessCase record);

    int updateByPrimaryKey(BusinessCase record);

    List<String> findAllFileName();

    List<BusinessCase> findAllBusinessCase();

    List<BusinessCase> findAllBusinessCaseByDescription(String description);
}