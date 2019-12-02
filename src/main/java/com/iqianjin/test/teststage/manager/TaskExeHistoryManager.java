package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.entity.TaskExeHistory;

import java.util.List;

/**
 * @author lixin
 * @creat 2019-08-13 下午05:18
 */
public interface TaskExeHistoryManager {
    void addTaskExeHistory(TaskExeHistory taskExeHistory);

    List<TaskExeHistory> getAllTaskRecordHistory();
}
