package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.TestCaseMapper;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.manager.TestCaseExcelUploadManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestCaseExcelUploadManagerImpl implements TestCaseExcelUploadManager {

    @Autowired
    private TestCaseMapper testCaseMapper;
    @Override
    public void saveExcelUploadTestCase(TestCase testCase) {
        testCaseMapper.insert(testCase);
    }
}
