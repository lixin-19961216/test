package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.AppPackageMapper;
import com.iqianjin.test.teststage.dto.AppPackageListDTO;
import com.iqianjin.test.teststage.entity.AppPackage;
import com.iqianjin.test.teststage.manager.AppPackageManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AppPackageManagerImpl implements AppPackageManager {
    @Autowired
    private AppPackageMapper appPackageMapper;

    @Override
    public AppPackage findById(Integer id){
        return appPackageMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return appPackageMapper.deleteById(id);
    }

    @Override
    public int insert(AppPackage appPackage) {
        return appPackageMapper.insert(appPackage);
    }

    @Override
    public int insertSelective(AppPackage record) {
        return appPackageMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AppPackage record) {
        return appPackageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AppPackage record) {
        return appPackageMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean updateFlagDelete(Integer id){
         if(appPackageMapper.updateFlagDelete(id)>0){
             return true;
         }
         return false;
    }
    @Override
    public List<AppPackage> findAll() {
        return appPackageMapper.findAll();
    }

    @Override
    public List<AppPackage> findByParams(AppPackageListDTO appPackageListDTO){

        return appPackageMapper.findByParams(appPackageListDTO);
    }

}
