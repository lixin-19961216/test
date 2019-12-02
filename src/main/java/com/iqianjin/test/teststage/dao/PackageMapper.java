package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.Package;

public interface PackageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Package record);

    int insertSelective(Package record);

    Package selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Package record);

    int updateByPrimaryKey(Package record);
}