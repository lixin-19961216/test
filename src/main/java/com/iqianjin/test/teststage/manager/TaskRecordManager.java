package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.entity.TaskRecord;

/**
 * @author lixin
 * @creat 2019-08-13 下午05:18
 */
public interface TaskRecordManager {
    int addTaskRecord(TaskRecord taskRecord);

    String findTestCaseIdsById(Integer id);

    int findMaxId();
}
