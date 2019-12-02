package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.TestCaseMapper;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.manager.TestCaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestCaseManagerImpl implements TestCaseManager {

    @Autowired
    TestCaseMapper testCaseMapper;

    @Override
    public List<TestCase> findTestCaseListByPlatInfo(String platInfo) {
        return testCaseMapper.findTestCaseListByPlatInfo(platInfo);
    }

    @Override
    public List<TestCase> findTestCaseListByPlatInfoAndModule(String platInfo, String featureModule) {
        return testCaseMapper.findTestCaseListByPlatInfoAndModule(platInfo, featureModule);
    }

    @Override
    public List<TestCase> findTestCaseListByPlatInfoAndDescription(String platInfo, String description) {
        return testCaseMapper.findTestCaseListByPlatInfoAndDescription(platInfo, description);
    }

    @Override
    public List<TestCase> findTestCaseListByPlatInfoAndModuleAndDescription(String platInfo, String featureModule, String description) {
        return testCaseMapper.findTestCaseListByPlatInfoAndModuleAndDescription(platInfo, featureModule, description);
    }

    @Override
    public void saveTestCase(TestCase testCase) {
        testCaseMapper.saveOneTestCase(testCase);
    }

    @Override
    public void delTestCaseById(Integer id) {
        testCaseMapper.delTestCaseById(id);
    }

    @Override
    public void modifyTestCase(TestCase testCase) {
        testCaseMapper.updateByPrimaryKeySelective(testCase);
    }

    @Override
    public TestCase findTestCaseById(Integer id) {
        return testCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Integer> findIdByPlatInfo(String platInfo) {
        return testCaseMapper.findIdByPlatInfo(platInfo);
    }

    @Override
    public List<Integer> findIdByPlatInfoAndModule(String platInfo, String featureModule) {
        return testCaseMapper.findIdByPlatInfoAndModule(platInfo,featureModule);
    }

    @Override
    public List<Integer> findIdByBranch(String platInfo, String featureModule,String branch) {
        return testCaseMapper.findIdByBranch(platInfo,featureModule,branch);
    }
}
