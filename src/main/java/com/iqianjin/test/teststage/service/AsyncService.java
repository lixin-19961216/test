package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.vo.RunTestCaseResultVO;

import java.util.List;
import java.util.concurrent.Future;

public interface AsyncService {
    /**
     * 执行异步任务
     * 执行ids中所包括的所有id的用例
     * @param ids
     * @param serverInfo
     */
    Future<RunTestCaseResultVO> executeAsyncById(List<Integer> ids, ServerInfo serverInfo);

    /**
     * 执行异步任务
     * 执行某个项目下的所有用例
     * @param platform
     * @param serverInfo
     */
    Future<RunTestCaseResultVO> executeAsyncByPlatForm(String platform, ServerInfo serverInfo);

    /**
     * 按项目、模块执行用例
     * @param platform
     * @param featureModule
     * @param serverInfo
     * @return
     */
    Future<RunTestCaseResultVO> executeAsyncByPlatForm(String platform,String featureModule,ServerInfo serverInfo);

    /**
     * 按项目、模块、分支运行用例
     * @param platform
     * @param featureModule
     * @param serverInfo
     * @param branch
     * @return
     */
    Future<RunTestCaseResultVO> executeAsyncByBranch(String platform,String featureModule,ServerInfo serverInfo,String branch);


}
