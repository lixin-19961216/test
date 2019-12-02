package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.TaskExeHistoryMapper;
import com.iqianjin.test.teststage.entity.TaskExeHistory;
import com.iqianjin.test.teststage.manager.TaskExeHistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskExeHistoryManagerImpl implements TaskExeHistoryManager {
    @Autowired
    TaskExeHistoryMapper taskExeHistoryMapper;

    @Override
    public void addTaskExeHistory(TaskExeHistory taskExeHistory) {
        taskExeHistoryMapper.insert(taskExeHistory);
    }

    @Override
    public List<TaskExeHistory> getAllTaskRecordHistory() {
        return taskExeHistoryMapper.getAllTaskRecordHistory();
    }
}
