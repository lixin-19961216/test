package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.module.ModuleListDTO;
import com.iqianjin.test.teststage.entity.ModuleRelationship;
import com.iqianjin.test.teststage.vo.ModuleListVO;

import java.util.List;

public interface ModuleRelationshipService {
    /**
     * 获取平台&模块对应关系列表
     * @param moduleListDTO
     * @return
     */
    Result<ModuleListVO> moduleList(ModuleListDTO moduleListDTO);

    /**
     * 根据id删除平台&模块对应关系
     * @param id
     */
    Result delModuleById(Integer id);

    /**
     * 编辑某个平台&模块对应关系
     * @param moduleRelationship
     */
    Result updateModule(ModuleRelationship moduleRelationship);

    /**
     * 新增平台&模块对应关系
     * @param moduleRelationship
     */
    Result addModule(ModuleRelationship moduleRelationship);

    /**
     * 获取所有的平台
     * @return
     */
    Result getAllPlatInfo();

    /**
     * 通过platInfo获取所有的功能模块
     * @param platInfo
     * @return
     */
    Result getFeatureModuleByPlatInfo(String platInfo);
}
