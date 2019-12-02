package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.ModuleRelationship;

import java.util.List;

public interface ModuleRelationshipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ModuleRelationship record);

    int insertSelective(ModuleRelationship record);

    ModuleRelationship selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModuleRelationship record);

    int updateByPrimaryKey(ModuleRelationship record);

    List<ModuleRelationship> findAllModule();

    List<ModuleRelationship> findModuleByPlatInfo(String platInfo);

    List<String> getAllPlatInfo();

    List<String> getFeatureModuleByPlatInfo(String platInfo);
}