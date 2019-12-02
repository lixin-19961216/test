package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.dto.AppPackageListDTO;
import com.iqianjin.test.teststage.entity.AppPackage;

import java.util.List;

public interface AppPackageManager {
    int deleteById(Integer id);

    int insert(AppPackage appPackage);

    int insertSelective(AppPackage record);

    AppPackage findById(Integer id);

    int updateByPrimaryKeySelective(AppPackage record);

    int updateByPrimaryKey(AppPackage record);

    boolean updateFlagDelete(Integer id);

    List<AppPackage> findAll();

    List<AppPackage> findByParams(AppPackageListDTO appPackageListDTO);
}
