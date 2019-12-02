package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.entity.TaskRecord;
import org.springframework.stereotype.Component;

public interface RunTestNGService {
    /**
     * 生成测试报告
     * @param taskRecordId
     * @param serverInfo
     * @return
     */
    String generateTestNGReport(Integer taskRecordId, ServerInfo serverInfo);
}
