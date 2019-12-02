package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.dto.AppPackageListDTO;
import com.iqianjin.test.teststage.entity.AppPackage;

import java.util.List;

public interface AppPackageMapper {
    int deleteById(Integer id);

    int insert(AppPackage appPackage);

    int insertSelective(AppPackage record);

    AppPackage selectById(Integer id);

    int updateByPrimaryKeySelective(AppPackage record);

    int updateByPrimaryKey(AppPackage record);
    int updateFlagDelete(Integer id);

    List<AppPackage> findAll();
    List<AppPackage> findByParams(AppPackageListDTO appPackageListDTO);
}