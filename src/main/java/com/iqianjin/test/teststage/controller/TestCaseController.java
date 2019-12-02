package com.iqianjin.test.teststage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.JenkinsResult;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.excel.UploadExcelReqDTO;
import com.iqianjin.test.teststage.dto.excel.UploadExcelResDTO;
import com.iqianjin.test.teststage.dto.module.ModuleListDTO;
import com.iqianjin.test.teststage.dto.testcase.*;
import com.iqianjin.test.teststage.entity.ModuleRelationship;
import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.service.*;
import com.iqianjin.test.teststage.vo.ModuleListVO;
import com.iqianjin.test.teststage.vo.RunTestCaseResultVO;
import com.iqianjin.test.teststage.vo.TestCaseListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequestMapping("testCase")
@Api("测试用例接口")
@Slf4j
public class TestCaseController {

    @Value("${spring.upload.dir}")
    private String uploadExcelDir;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private ModuleRelationshipService moduleRelationshipService;

    @Autowired
    private TaskExeHistoryService taskExeHistoryService;

    //使用线程池来处理
    @Autowired
    private AsyncService asyncService;

    private static final long MAX_FILE_SIZE = 30*1024*1024;

    @PostMapping("/uploadExcel")
    @ApiOperation("上传excel接口，记住表格里的plat_info一定不能为空")
    public Result<UploadExcelResDTO> uploadExcel(@RequestBody UploadExcelReqDTO reqDTO){

        log.info("开始上传文件，文件名为:{}",reqDTO.getFile().getName());

        MultipartFile file = reqDTO.getFile();
        try {
            if (file != null){
                String orginFileName = file.getOriginalFilename();//getName : 获取表单中文件组件的名字;getOriginalFilename : 获取上传文件的原名
                String fileNameLeft = orginFileName.substring(0, orginFileName.lastIndexOf("."));
                String fileNameRight = orginFileName.substring(orginFileName.lastIndexOf("."));
                long size = file.getSize();

                if (!fileNameRight.contains("xls")){
                    return Result.failMsg("上传文件类型错误，目前只支持excel！");
                }
                if (size == 0){
                    return Result.failMsg("上传文件为空，请重新选择文件上传！");
                }
                if (size > MAX_FILE_SIZE){
                    return Result.failMsg("上传文件过大，最大不超过30M，请重新选择文件上传！");
                }

                File localFile = new File(uploadExcelDir, fileNameLeft + String.valueOf(System.currentTimeMillis()) + fileNameRight);

                try{
                    localFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(localFile);
                    fos.write(file.getBytes());
                    fos.close();
                }catch (Exception e){
                    log.info("上传文件出错{}", e.getMessage());
                    e.printStackTrace();
                }
            }
            Result result = testCaseService.readExceltToTestCase(file);
            UploadExcelResDTO uploadExcelResDTO = UploadExcelResDTO.builder().msg(result.getMsg()).build();
            return Result.success(uploadExcelResDTO);
        } catch (IOException e) {
            log.info("上传excel后，入库失败。");
            e.printStackTrace();
            return Result.failMsg("上传excel后，入库失败。");
        }
    }

    @PostMapping("/list")
    @ApiOperation("获取测试用例列表接口")
    public Result<TestCaseListVO> testCaseList(@RequestBody TestCaseListByTypeReqDTO typeReqDTO){
        return testCaseService.testCaseList(typeReqDTO);
    }

    @PostMapping("/add")
    @ApiOperation("手动新增一条case")
    public Result addTestCase(@RequestBody TestCaseSaveReqDTO testCaseDTO){
        return testCaseService.saveTestCase(testCaseDTO);
    }

    @GetMapping("/del")
    @ApiOperation("删除一条用例")
    public Result delTestCase(@RequestParam(value = "id") Integer id){
        return testCaseService.delTestCaseByIds(id);
    }

    @PostMapping("/modify")
    @ApiOperation("编辑单条用例")
    public Result modifyTestCase(@RequestBody TestCase testCase){
        return testCaseService.modifyTestCase(testCase);
    }

    @PostMapping("/runTestCaseById")
    @ApiOperation("根据id运行单个或多个用例")
    public Result runTestCaseById(@RequestBody TestCaseRunReqDTO testCaseRunReqDTO){
        List<Integer> list = testCaseRunReqDTO.getIds();
        ServerInfo serverInfo = serverInfoService.findServerInfoByApiUrl(testCaseRunReqDTO.getApiUrl());
        Future<RunTestCaseResultVO> future = asyncService.executeAsyncById(list, serverInfo);
        RunTestCaseResultVO result = new RunTestCaseResultVO();

//        if (!future.isDone()){
//            return Result.failMsg("线程还未全部执行完成");
//        }

        try {
            result = future.get();
            log.info("return result is " + result);
            if (result.getFail_flag() == "1"){
                return Result.failMsg("运行用例失败").setData(result);
            }
            if (result.getFail_flag() == "3"){
                return Result.failMsg("运行异常").setData(result);
            }
        }catch (Exception e){
            log.info("运行用例失败");
            e.printStackTrace();
        }
        return Result.success(result).setMsg("运行用例成功");
    }

    @PostMapping("/runTestCaseByplatform")
    @ApiOperation("按platForm项目类型运行用例")
    public JenkinsResult runTestCaseByplatform(@RequestBody TestCaseRunByPlatformReqDTO testCaseRunByPlatformReqDTO){
        String platform = testCaseRunByPlatformReqDTO.getPlatform();
        ServerInfo serverInfo = serverInfoService.findServerInfoByApiUrl(testCaseRunByPlatformReqDTO.getApiUrl());
        RunTestCaseResultVO result = new RunTestCaseResultVO();
        Future<RunTestCaseResultVO> future = asyncService.executeAsyncByPlatForm(platform, serverInfo);

//        if (!future.isDone()){
//            return Result.failMsg("线程还未全部执行完成");
//        }

        try {
            result = future.get();
            log.info("return result is " + result);
            if (result.getFail_flag() == "1"){
                return JenkinsResult.builder().setCode(-1)
                                    .setSuccessRate(result.getSuccess_rate())
                                    .setMsg("运行接口用例失败,通过率为" + result.getSuccess_rate() + "%")
                                    .setReportUrl(result.getReportUrl());
            }
            if (result.getFail_flag() == "2"){
                return JenkinsResult.builder().setCode(-1)
                                    .setSuccessRate(0.0)
                                    .setMsg("该platform下对应的用例数为0")
                                    .setReportUrl("无接口测试报告");
            }
            if (result.getFail_flag() == "3"){
                return JenkinsResult.builder().setCode(-1)
                                    .setSuccessRate(0.0)
                                    .setMsg("运行异常")
                                    .setReportUrl("无接口测试报告");
            }
        }catch (Exception e){
            log.info("运行用例失败");
            e.printStackTrace();
        }
        return JenkinsResult.builder().setCode(1)
                            .setSuccessRate(result.getSuccess_rate())
                            .setMsg("运行成功")
                            .setReportUrl(result.getReportUrl());
//        return Result.success(result).setMsg("运行用例成功");
    }
    @PostMapping("/runTestCaseByFeatureModule")
    @ApiOperation("按platForm项目类型和FeatureModule模块运行用例")
    public JenkinsResult runTestCaseByFeatureModule(@RequestBody TestCaseRunByFeatureModuleReqDTO testCaseRunByFeatureModuleReqDTO){
        String platform = testCaseRunByFeatureModuleReqDTO.getPlatform();
        String featureModule=testCaseRunByFeatureModuleReqDTO.getFeatureModule();
        ServerInfo serverInfo = serverInfoService.findServerInfoByApiUrl(testCaseRunByFeatureModuleReqDTO.getApiUrl());
        RunTestCaseResultVO result = new RunTestCaseResultVO();
        Future<RunTestCaseResultVO> future = asyncService.executeAsyncByPlatForm(platform,featureModule,serverInfo);

//        if (!future.isDone()){
//            return Result.failMsg("线程还未全部执行完成");
//        }

        try {
            result = future.get();
            log.info("return result is " + result);
            if (result.getFail_flag() == "1"){
                return JenkinsResult.builder().setCode(-1)
                                    .setSuccessRate(result.getSuccess_rate())
                                    .setMsg("运行接口用例失败,通过率为" + result.getSuccess_rate() + "%")
                                    .setReportUrl(result.getReportUrl());
            }
            if (result.getFail_flag() == "2"){
                return JenkinsResult.builder().setCode(-1)
                                    .setSuccessRate(0.0)
                                    .setMsg("该platform下对应的用例数为0")
                                    .setReportUrl("无接口测试报告");
            }
            if (result.getFail_flag() == "3"){
                return JenkinsResult.builder().setCode(-1)
                                    .setSuccessRate(0.0)
                                    .setMsg("运行异常")
                                    .setReportUrl("无接口测试报告");
            }
        }catch (Exception e){
            log.info("运行用例失败");
            e.printStackTrace();
        }
        return JenkinsResult.builder().setCode(1)
                            .setSuccessRate(result.getSuccess_rate())
                            .setMsg("运行成功")
                            .setReportUrl(result.getReportUrl());
    }

    @PostMapping("/runTestCaseByBranch")
    @ApiOperation("根据参数运行接口测试")
    public JenkinsResult runTestCaseByBranch(TestCaseRunDTO testCaseRunDTO){
        String platInfo =testCaseRunDTO.getPlatInfo();
        String featureModule=testCaseRunDTO.getFeatureModule();
        String branch=testCaseRunDTO.getBranch();
        ServerInfo serverInfo = serverInfoService.findServerInfoByApiUrl(testCaseRunDTO.getApiUrl());
        RunTestCaseResultVO result = new RunTestCaseResultVO();
        Future<RunTestCaseResultVO> future = asyncService.executeAsyncByBranch(platInfo,featureModule,serverInfo,branch);

//        if (!future.isDone()){
//            return Result.failMsg("线程还未全部执行完成");
//        }

        try {
            result = future.get();
            log.info("return result is " + result);
            if (result.getFail_flag() == "1"){
                return JenkinsResult.builder().setCode(-1)
                        .setSuccessRate(result.getSuccess_rate())
                        .setMsg("运行接口用例失败,通过率为" + result.getSuccess_rate() + "%")
                        .setReportUrl(result.getReportUrl());
            }
            if (result.getFail_flag() == "2"){
                return JenkinsResult.builder().setCode(-1)
                        .setSuccessRate(0.0)
                        .setMsg("该platform下对应的用例数为0")
                        .setReportUrl("无接口测试报告");
            }
            if (result.getFail_flag() == "3"){
                return JenkinsResult.builder().setCode(-1)
                        .setSuccessRate(0.0)
                        .setMsg("运行异常")
                        .setReportUrl("无接口测试报告");
            }
        }catch (Exception e){
            log.info("运行用例失败");
            e.printStackTrace();
        }
        return JenkinsResult.builder().setCode(1)
                .setSuccessRate(result.getSuccess_rate())
                .setMsg("运行成功")
                .setReportUrl(result.getReportUrl());
    }

    @PostMapping("/moduleList")
    @ApiOperation("获取配置信息列表&模糊查询")
    public Result<ModuleListVO> moduleList(@RequestBody ModuleListDTO moduleListDTO){
        return moduleRelationshipService.moduleList(moduleListDTO);
    }

    @PostMapping("/addModule")
    @ApiOperation("新增一个平台&模块对应关系")
    public Result addModule(@RequestBody ModuleRelationship moduleRelationship){
        return moduleRelationshipService.addModule(moduleRelationship);
    }

    @GetMapping("/delModule")
    @ApiOperation("根据id删除平台&模块对应关系")
    public Result delModule(@RequestParam(value = "id") Integer id){
        return moduleRelationshipService.delModuleById(id);
    }

    @PostMapping("/modifyModule")
    @ApiOperation("编辑已有的平台&模块对应关系")
    public Result modifyModule(@RequestBody ModuleRelationship moduleRelationship){
        return moduleRelationshipService.updateModule(moduleRelationship);
    }

    @GetMapping("/getAllPlatInfo")
    @ApiModelProperty("获取所有的平台类型")
    public Result getAllPlatInfo(){
        return moduleRelationshipService.getAllPlatInfo();
    }
    @GetMapping("/getFeatureModuleByPlatInfo")
    @ApiModelProperty("通过platInfo获取所有的功能模块")
    public Result getFeatureModuleByPlatInfo(@RequestParam(value = "platInfo") String platInfo){
        return moduleRelationshipService.getFeatureModuleByPlatInfo(platInfo);
    }

    @PostMapping("getAllTaskRecordHistory")
    @ApiModelProperty("获取所有测试用例运行记录")
    public Result taskRecordHistory(@RequestBody TaskRecordHistoryDTO taskRecordHistoryDTO){
        return taskExeHistoryService.getAllTaskRecordHistory(taskRecordHistoryDTO);
    }
}
