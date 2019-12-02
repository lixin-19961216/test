package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.ModuleRelationshipMapper;
import com.iqianjin.test.teststage.entity.ModuleRelationship;
import com.iqianjin.test.teststage.manager.ModuleRelationshipManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleRelationshipManagerImpl implements ModuleRelationshipManager {
    @Autowired
    ModuleRelationshipMapper moduleRelationshipMapper;

    @Override
    public List<ModuleRelationship> findAllModule() {
        return moduleRelationshipMapper.findAllModule();
    }

    @Override
    public List<ModuleRelationship> findModuleByPlatInfo(String platInfo) {
        return moduleRelationshipMapper.findModuleByPlatInfo(platInfo);
    }

    @Override
    public void delModuleById(Integer id) {
        moduleRelationshipMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateModule(ModuleRelationship moduleRelationship) {
        moduleRelationshipMapper.updateByPrimaryKeySelective(moduleRelationship);
    }

    @Override
    public void addModule(ModuleRelationship moduleRelationship) {
        moduleRelationshipMapper.insert(moduleRelationship);
    }

    @Override
    public List<String> getAllPlatInfo() {
        return moduleRelationshipMapper.getAllPlatInfo();
    }

    @Override
    public List<String> getFeatureModuleByPlatInfo(String platInfo) {
        return moduleRelationshipMapper.getFeatureModuleByPlatInfo(platInfo);
    }
}
