package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.TestCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestCaseMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(TestCase record);

    int insertSelective(TestCase record);

    TestCase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestCase record);

    void updateByPrimaryKey(TestCase record);

    List<TestCase> findTestCaseListByPlatInfo(String platInfo);

    List<TestCase> findTestCaseListByPlatInfoAndModule(@Param("platInfo") String platInfo, @Param("featureModule") String featureModule);

    List<TestCase> findTestCaseListByPlatInfoAndDescription(@Param("platInfo") String platInfo, @Param("description") String description);

    List<TestCase> findTestCaseListByPlatInfoAndModuleAndDescription(@Param("platInfo") String platInfo, @Param("featureModule") String featureModule, @Param("description") String description);

    void saveOneTestCase(TestCase testCase);

    void delTestCaseById(Integer id);

    List<Integer> findIdByPlatInfo(String platInfo);

    List<Integer> findIdByPlatInfoAndModule(@Param("platInfo")String platInfo, @Param("featureModule")String featureModule);
    List<Integer> findIdByBranch(@Param("platInfo")String platInfo, @Param("featureModule")String featureModule, @Param("branch")String branch);

}
