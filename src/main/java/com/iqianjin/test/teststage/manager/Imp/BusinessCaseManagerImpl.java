package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.BusinessCaseMapper;
import com.iqianjin.test.teststage.entity.BusinessCase;
import com.iqianjin.test.teststage.manager.BusinessCaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessCaseManagerImpl implements BusinessCaseManager {

    @Autowired
    BusinessCaseMapper businessCaseMapper;

    @Override
    public void saveBusinessCase(BusinessCase businessCase) {
        businessCaseMapper.insert(businessCase);
    }

    @Override
    public List<String> findAllFileName() {
        return businessCaseMapper.findAllFileName();
    }

    @Override
    public List<BusinessCase> findAllBusinessCase() {
        return businessCaseMapper.findAllBusinessCase();
    }

    @Override
    public List<BusinessCase> findAllBusinessCaseByDescription(String description) {
        return businessCaseMapper.findAllBusinessCaseByDescription(description);
    }

    @Override
    public void updateBusinessCase(BusinessCase businessCase) {
        businessCaseMapper.updateByPrimaryKey(businessCase);
    }

    @Override
    public void deleteBusinessCaseById(Integer id) {
        businessCaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public BusinessCase findBusinessCaseById(Integer id) {
        return businessCaseMapper.selectByPrimaryKey(id);
    }
}
