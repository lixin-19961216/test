package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.testcase.TaskRecordHistoryDTO;

/**
 * 运行记录相关
 */
public interface TaskExeHistoryService {
    /**
     * 默认获取所有的接口测试运行记录
     * @return
     */
    Result getAllTaskRecordHistory(TaskRecordHistoryDTO taskRecordHistoryDTO);
}
