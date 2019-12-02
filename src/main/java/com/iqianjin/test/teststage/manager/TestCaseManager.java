package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.entity.TestCase;

import java.util.List;

/**
 * @author lixin
 * @creat 2019-08-02 下午06:18
 */
public interface TestCaseManager {
    List<TestCase> findTestCaseListByPlatInfo(String platInfo);

    List<TestCase> findTestCaseListByPlatInfoAndModule(String platInfo, String featureModule);

    List<TestCase> findTestCaseListByPlatInfoAndDescription(String platInfo, String description);

    List<TestCase> findTestCaseListByPlatInfoAndModuleAndDescription(String platInfo, String featureModule, String description);

    void saveTestCase(TestCase testCase);

    void delTestCaseById(Integer id);

    void modifyTestCase(TestCase testCase);

    TestCase findTestCaseById(Integer id);

    List<Integer> findIdByPlatInfo(String platform);

    List<Integer> findIdByPlatInfoAndModule(String platInfo,String featureModule);

    List<Integer> findIdByBranch(String platInfo,String featureModule,String branch);

}
