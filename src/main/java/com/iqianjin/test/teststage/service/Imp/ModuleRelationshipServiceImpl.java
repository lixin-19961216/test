package com.iqianjin.test.teststage.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.module.ModuleListDTO;
import com.iqianjin.test.teststage.entity.ModuleRelationship;
import com.iqianjin.test.teststage.manager.ModuleRelationshipManager;
import com.iqianjin.test.teststage.service.ModuleRelationshipService;
import com.iqianjin.test.teststage.vo.ModuleListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ModuleRelationshipServiceImpl implements ModuleRelationshipService {
    @Autowired
    ModuleRelationshipManager moduleRelationshipManager;

    @Override
    public Result<ModuleListVO> moduleList(ModuleListDTO moduleListDTO) {
        PageHelper.startPage(moduleListDTO.getPageNum(), moduleListDTO.getPageSize());

        ModuleListVO moduleListVO = new ModuleListVO();
        List<ModuleRelationship> list;
        if (StringUtils.isEmpty(moduleListDTO.getPlatInfo())){
            try {
                list = moduleRelationshipManager.findAllModule();
                log.info("查询平台&模块对应关系成功！{}", list);
            }catch (Exception e){
                log.info("查询平台&模块对应关系失败！");
                return Result.failMsg("查询平台&模块对应关系失败");
            }
        }else {
            try {
                list = moduleRelationshipManager.findModuleByPlatInfo(moduleListDTO.getPlatInfo());
                log.info("根据platInfo查询平台&模块对应关系成功！{}", list);
            }catch (Exception e){
                log.info("根据platInfo查询平台&模块对应关系失败！");
                return Result.failMsg("根据platInfo查询平台&模块对应关系失败");
            }
        }
        PageInfo<ModuleRelationship> pageInfo = new PageInfo<>(list);
        moduleListVO.setServerInfoList(pageInfo);
        return Result.success(moduleListVO);
    }

    @Override
    public Result delModuleById(Integer id) {
        try {
            log.info("要删除模块的id为{}", id);
            moduleRelationshipManager.delModuleById(id);
        }catch (Exception e){
            log.info("删除模块id为{}失败", id);
            e.printStackTrace();
            return Result.failMsg("删除失败！");
        }
        return Result.success("删除成功！");
    }

    @Override
    public Result updateModule(ModuleRelationship moduleRelationship) {
        try {
            log.info("开始编辑已有的平台&模块对应关系，{},{}", moduleRelationship.getPlatInfo(), moduleRelationship.getFeatureModule());
            moduleRelationshipManager.updateModule(moduleRelationship);
            log.info("编辑成功！");
        }catch (Exception e){
            log.info("编辑失败！");
            e.printStackTrace();
            return Result.failMsg("编辑失败！");
        }
        return Result.success("编辑成功！");
    }

    @Override
    public Result addModule(ModuleRelationship moduleRelationship) {
        try {
            moduleRelationship.setCreateDate(new Date());
            log.info("添加一个新的平台&模块对应关系，{},{}", moduleRelationship.getPlatInfo(), moduleRelationship.getFeatureModule());
            moduleRelationshipManager.addModule(moduleRelationship);
            log.info("添加成功！");
        }catch (Exception e){
            log.info("添加失败！");
            e.printStackTrace();
            return Result.failMsg("添加失败！");
        }
        return Result.success("添加成功！");
    }

    @Override
    public Result getAllPlatInfo() {
        List<String> list;
        try {
            log.info("开始获取所有平台类型");
            list = moduleRelationshipManager.getAllPlatInfo();
        }catch (Exception e){
            e.printStackTrace();
            return Result.failMsg("获取所有平台失败");
        }
        return Result.success(list);
    }

    @Override
    public Result getFeatureModuleByPlatInfo(String platInfo) {
        List<String> list;
        try {
            log.info("开始获取{}平台类型下的所有功能模块", platInfo);
            list = moduleRelationshipManager.getFeatureModuleByPlatInfo(platInfo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failMsg("获取失败");
        }
        return Result.success(list);
    }
}
