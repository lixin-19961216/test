package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.TaskRecordMapper;
import com.iqianjin.test.teststage.entity.TaskRecord;
import com.iqianjin.test.teststage.manager.TaskRecordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskRecordManagerImpl implements TaskRecordManager {
    @Autowired
    TaskRecordMapper taskRecordMapper;

    @Override
    public int addTaskRecord(TaskRecord taskRecord) {
        return taskRecordMapper.insert(taskRecord);
    }

    @Override
    public String findTestCaseIdsById(Integer id) {
        return taskRecordMapper.selectTestCaseIdsById(id);
    }

    @Override
    public int findMaxId() {
        return taskRecordMapper.findMaxId();
    }
}
