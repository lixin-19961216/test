package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.entity.ModuleRelationship;

import java.util.List;

/**
 * @author lixin
 * @creat 2019-08-13 下午05:18
 */
public interface ModuleRelationshipManager {
    List<ModuleRelationship> findAllModule();

    List<ModuleRelationship> findModuleByPlatInfo(String platInfo);

    void delModuleById(Integer id);

    void updateModule(ModuleRelationship moduleRelationship);

    void addModule(ModuleRelationship moduleRelationship);

    List<String> getAllPlatInfo();

    List<String> getFeatureModuleByPlatInfo(String platInfo);
}
