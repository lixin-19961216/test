package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.entity.BusinessCase;

import java.util.List;

public interface BusinessCaseManager {
    void saveBusinessCase(BusinessCase businessCase);

    List<String> findAllFileName();

    List<BusinessCase> findAllBusinessCase();

    List<BusinessCase> findAllBusinessCaseByDescription(String description);

    void updateBusinessCase(BusinessCase businessCase);

    void deleteBusinessCaseById(Integer id);

    BusinessCase findBusinessCaseById(Integer id);
}
