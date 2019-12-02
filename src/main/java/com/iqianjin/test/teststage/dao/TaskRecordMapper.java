package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.TaskRecord;


public interface TaskRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskRecord record);

    int insertSelective(TaskRecord record);

    TaskRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskRecord record);

    int updateByPrimaryKey(TaskRecord record);

    String selectTestCaseIdsById(Integer id);

    /**
     * 找到task_record表中id的最大值
     * @return
     */
    int findMaxId();
}