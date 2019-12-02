package com.iqianjin.test.teststage.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.testcase.TaskRecordHistoryDTO;
import com.iqianjin.test.teststage.entity.TaskExeHistory;
import com.iqianjin.test.teststage.manager.TaskExeHistoryManager;
import com.iqianjin.test.teststage.service.TaskExeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskExeHistoryServiceImpl implements TaskExeHistoryService {

    @Autowired
    private TaskExeHistoryManager taskExeHistoryManager;

    @Override
    public Result getAllTaskRecordHistory(TaskRecordHistoryDTO taskRecordHistoryDTO) {
        PageHelper.startPage(taskRecordHistoryDTO.getPageNum(), taskRecordHistoryDTO.getPageSize());
        List<TaskExeHistory> list;
        try {
            list = taskExeHistoryManager.getAllTaskRecordHistory();
        }catch (Exception e){
            e.printStackTrace();
            return Result.failMsg("查找所有运行记录失败");
        }
        PageInfo<TaskExeHistory> taskExeHistoryList = new PageInfo<>(list);
        return Result.success(taskExeHistoryList);
    }
}
