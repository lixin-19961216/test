package com.iqianjin.test.teststage.service.Imp;

import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.entity.TaskRecord;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.manager.TaskRecordManager;
import com.iqianjin.test.teststage.manager.TestCaseManager;
import com.iqianjin.test.teststage.service.AsyncService;
import com.iqianjin.test.teststage.service.RunTestNGService;
import com.iqianjin.test.teststage.utils.TransformUtil;
import com.iqianjin.test.teststage.vo.RunTestCaseResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    TaskRecordManager taskRecordManager;

    @Autowired
    RunTestNGService runTestNGService;

    @Autowired
    TestCaseManager testCaseManager;

    public static ThreadLocal<String> threadLocalSuccCount = new ThreadLocal<String>();

    public static ThreadLocal<String> threadLocalFailCount = new ThreadLocal<String>();

    public static ThreadLocal<String> FAIL_FLAG = new ThreadLocal<>();

    public static ThreadLocal<Integer> SUCCESS_COUNT = new ThreadLocal<>();

    public static ThreadLocal<Integer> FAIL_COUNT = new ThreadLocal<>();

    public static ThreadLocal<Double> SUCCESS_RATE = new ThreadLocal<>();

    @Override
    @Async("asyncServiceExecutor")
    public Future<RunTestCaseResultVO> executeAsyncById(List<Integer> ids, ServerInfo serverInfo) {

        String string = TransformUtil.listToString(ids);

        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setInterfaceId(string);
        taskRecord.setReportUrl("");
        taskRecord.setDescription("");
        taskRecord.setTitle("执行接口用例");
        taskRecord.setCreateTime(new Date());
        taskRecord.setUpdateTime(new Date());
        taskRecord.setFlag("0");
        //为了获取非任务方式运行后的succCount和failCount值
        threadLocalSuccCount.set("single");
        threadLocalFailCount.set("single");
        RunTestCaseResultVO resultVO = new RunTestCaseResultVO();
        try {
            log.info("开始保存TaskRecord任务，包含的用例id有{}", ids);
            log.info("开始按照用例id来运行{}", ids);
            Integer temp = taskRecordManager.findMaxId();
            taskRecord.setId(temp + 1);
            taskRecordManager.addTaskRecord(taskRecord);
            log.info("保存TaskRecord任务成功，对应的TaskRecord任务的id为{}", taskRecord.getId());

            String result = runTestNGService.generateTestNGReport(taskRecord.getId(), serverInfo);

            if (!result.equals("fail")){
                resultVO.setTime(result);
                resultVO.setSuccess_count(AsyncServiceImpl.SUCCESS_COUNT.get());
                resultVO.setFail_count(AsyncServiceImpl.FAIL_COUNT.get());
                resultVO.setSuccess_rate(AsyncServiceImpl.SUCCESS_RATE.get());
                if (FAIL_COUNT.get() > 0) {
                    resultVO.setFail_flag("1");
                } else {
                    resultVO.setFail_flag("0");
                }
//                resultVO.setFail_flag(AsyncServiceImpl.FAIL_FLAG.get());
                resultVO.setReportUrl("http://10.10.201.39:9090/" + result + "/index.html");
                return new AsyncResult<>(resultVO);
            }else {
                resultVO.setFail_flag("1");
                return new AsyncResult<>(resultVO);
            }
        }catch (Exception e){
            log.info("保存TaskRecord任务失败，包含的用例id有{}", ids);
            e.printStackTrace();
            resultVO.setFail_flag("3");
            return new AsyncResult<>(resultVO);
        }
    }

    @Override
    @Async("asyncServiceExecutor")
    public Future<RunTestCaseResultVO> executeAsyncByPlatForm(String platInfo, ServerInfo serverInfo) {
        List<Integer> list = testCaseManager.findIdByPlatInfo(platInfo);
        String string = TransformUtil.listToString(list);
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setInterfaceId(string);
        taskRecord.setReportUrl("");
        taskRecord.setDescription("");
        taskRecord.setTitle("执行接口用例");
        taskRecord.setCreateTime(new Date());
        taskRecord.setUpdateTime(new Date());
        taskRecord.setFlag("0");
        RunTestCaseResultVO resultVO = new RunTestCaseResultVO();

        //判断是否有用例
        if (list.size() == 0) {
            resultVO.setFail_flag("2");
            return new AsyncResult<>(resultVO);
        }

        //为了获取非任务方式运行后的succCount和failCount值
        threadLocalSuccCount.set("single");
        threadLocalFailCount.set("single");
        try {
            log.info("开始保存TaskRecord任务，包含的用例id有{}", list);
            log.info("开始按照项目类型来运行{}", platInfo);
            Integer temp = taskRecordManager.findMaxId();
            taskRecord.setId(temp + 1);
            taskRecordManager.addTaskRecord(taskRecord);
            log.info("保存TaskRecord任务成功，对应的TaskRecord任务的id为{}", taskRecord.getId());

            String result = runTestNGService.generateTestNGReport(taskRecord.getId(), serverInfo);

            if (!result.equals("fail")){
                resultVO.setTime(result);
                resultVO.setSuccess_count(AsyncServiceImpl.SUCCESS_COUNT.get());
                resultVO.setFail_count(AsyncServiceImpl.FAIL_COUNT.get());
                resultVO.setSuccess_rate(AsyncServiceImpl.SUCCESS_RATE.get());
                if (FAIL_COUNT.get() > 0) {
                    resultVO.setFail_flag("1");
                } else {
                    resultVO.setFail_flag("0");
                }
//                resultVO.setFail_flag(AsyncServiceImpl.FAIL_FLAG.get());
                resultVO.setReportUrl("http://10.10.201.39:9090/" + result + "/index.html");
                return new AsyncResult<>(resultVO);
            }else {
                resultVO.setFail_flag("1");
                return new AsyncResult<>(resultVO);
            }
        }catch (Exception e){
            log.info("保存TaskRecord任务失败，包含的用例id有{}", list);
            e.printStackTrace();
            resultVO.setFail_flag("3");
            return new AsyncResult<>(resultVO);
        }
    }

    @Override
    @Async("asyncServiceExecutor")
    public Future<RunTestCaseResultVO> executeAsyncByPlatForm(String platInfo, String featureModule, ServerInfo serverInfo) {
        List<Integer> list = testCaseManager.findIdByPlatInfoAndModule(platInfo,featureModule);
        String string = TransformUtil.listToString(list);
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setInterfaceId(string);
        taskRecord.setReportUrl("");
        taskRecord.setDescription("");
        taskRecord.setTitle(platInfo+"项目"+featureModule+"测试用例");
        taskRecord.setCreateTime(new Date());
        taskRecord.setUpdateTime(new Date());
        taskRecord.setFlag("0");
        taskRecord.setDescription("");
        RunTestCaseResultVO resultVO = new RunTestCaseResultVO();

        //判断是否有用例
        if (list.size() == 0) {
            resultVO.setFail_flag("2");
            return new AsyncResult<>(resultVO);
        }

        //为了获取非任务方式运行后的succCount和failCount值
        threadLocalSuccCount.set("single");
        threadLocalFailCount.set("single");
        try {
            log.info("开始保存TaskRecord任务，包含的用例id有{}", list);
            log.info("开始按照项目类型及模块来运行{}", platInfo,":",featureModule);
            Integer temp = taskRecordManager.findMaxId();
            taskRecord.setId(temp + 1);
            taskRecordManager.addTaskRecord(taskRecord);
            log.info("保存TaskRecord任务成功，对应的TaskRecord任务的id为{}", taskRecord.getId());

            String result = runTestNGService.generateTestNGReport(taskRecord.getId(), serverInfo);

            if (!result.equals("fail")){
                resultVO.setTime(result);
                resultVO.setSuccess_count(AsyncServiceImpl.SUCCESS_COUNT.get());
                resultVO.setFail_count(AsyncServiceImpl.FAIL_COUNT.get());
                resultVO.setSuccess_rate(AsyncServiceImpl.SUCCESS_RATE.get());
                if (FAIL_COUNT.get() > 0) {
                    resultVO.setFail_flag("1");
                } else {
                    resultVO.setFail_flag("0");
                }
//                resultVO.setFail_flag(AsyncServiceImpl.FAIL_FLAG.get());
                resultVO.setReportUrl("http://10.10.201.39:9090/" + result + "/index.html");
                return new AsyncResult<>(resultVO);
            }else {
                resultVO.setFail_flag("1");
                return new AsyncResult<>(resultVO);
            }
        }catch (Exception e){
            log.info("保存TaskRecord任务失败，包含的用例id有{}", list);
            e.printStackTrace();
            resultVO.setFail_flag("3");
            return new AsyncResult<>(resultVO);
        }
    }

    @Override
    @Async("asyncServiceExecutor")
    public Future<RunTestCaseResultVO> executeAsyncByBranch(String platInfo, String featureModule, ServerInfo serverInfo,String branch) {
        if(branch!=null){
            if(branch.equals("dev")||branch.equals("master")){
                branch=null;
            }
        }
        List<Integer> list = testCaseManager.findIdByBranch(platInfo,featureModule,branch);
        String string = TransformUtil.listToString(list);
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setInterfaceId(string);
        taskRecord.setReportUrl("");
        taskRecord.setDescription("");
        taskRecord.setTitle(platInfo+"项目"+featureModule+"测试用例");
        taskRecord.setCreateTime(new Date());
        taskRecord.setUpdateTime(new Date());
        taskRecord.setFlag("0");
        taskRecord.setDescription("");
        RunTestCaseResultVO resultVO = new RunTestCaseResultVO();

        //判断是否有用例
        if (list.size() == 0) {
            resultVO.setFail_flag("2");
            return new AsyncResult<>(resultVO);
        }

        //为了获取非任务方式运行后的succCount和failCount值
        threadLocalSuccCount.set("single");
        threadLocalFailCount.set("single");
        try {
            log.info("开始保存TaskRecord任务，包含的用例id有{}", list);
            log.info("开始按照项目类型及模块来运行{}", platInfo,":",featureModule);
            Integer temp = taskRecordManager.findMaxId();
            taskRecord.setId(temp + 1);
            taskRecordManager.addTaskRecord(taskRecord);
            log.info("保存TaskRecord任务成功，对应的TaskRecord任务的id为{}", taskRecord.getId());

            String result = runTestNGService.generateTestNGReport(taskRecord.getId(), serverInfo);

            if (!result.equals("fail")){
                resultVO.setTime(result);
                resultVO.setSuccess_count(AsyncServiceImpl.SUCCESS_COUNT.get());
                resultVO.setFail_count(AsyncServiceImpl.FAIL_COUNT.get());
                resultVO.setSuccess_rate(AsyncServiceImpl.SUCCESS_RATE.get());
                if (FAIL_COUNT.get() > 0) {
                    resultVO.setFail_flag("1");
                } else {
                    resultVO.setFail_flag("0");
                }
//                resultVO.setFail_flag(AsyncServiceImpl.FAIL_FLAG.get());
                resultVO.setReportUrl("http://10.10.201.39:9090/" + result + "/index.html");
                return new AsyncResult<>(resultVO);
            }else {
                resultVO.setFail_flag("1");
                return new AsyncResult<>(resultVO);
            }
        }catch (Exception e){
            log.info("保存TaskRecord任务失败，包含的用例id有{}", list);
            e.printStackTrace();
            resultVO.setFail_flag("3");
            return new AsyncResult<>(resultVO);
        }
    }

}
