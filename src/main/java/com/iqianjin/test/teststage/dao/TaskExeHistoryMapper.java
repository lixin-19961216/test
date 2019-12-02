package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.TaskExeHistory;

import java.util.List;

public interface TaskExeHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(TaskExeHistory record);

    int insertSelective(TaskExeHistory record);

    TaskExeHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskExeHistory record);

    int updateByPrimaryKey(TaskExeHistory record);

    List<TaskExeHistory> getAllTaskRecordHistory();
}