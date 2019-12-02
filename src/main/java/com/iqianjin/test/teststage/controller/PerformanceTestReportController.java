package com.iqianjin.test.teststage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.performanceReport.GenerateChartDTO;
import com.iqianjin.test.teststage.dto.performanceReport.ReportListDTO;
import com.iqianjin.test.teststage.dto.performanceReport.UploadPerformanceTestReportDTO;
import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import com.iqianjin.test.teststage.manager.PerformanceTestReportManager;
import com.iqianjin.test.teststage.service.PerformanceTestReportService;
import com.iqianjin.test.teststage.vo.PerformanceReportVO;
import com.iqianjin.test.teststage.vo.ReportListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("testReport")
@Api("性能测试报告相关接口")
@Slf4j
public class PerformanceTestReportController {
    @Value("${performanceTestReport.dir}")
    private String  UPLOAD_DIR;

    @Autowired
    PerformanceTestReportService performanceTestReportService;

    @Autowired
    PerformanceTestReportManager performanceTestReportManager;

//    @PostMapping("/upload")
//    @ApiModelProperty("上传txt文件,保存性能测试报告的数据,仅支持.txt文件")
//    public Result uploadTxt(@RequestParam MultipartFile file,
//                            UploadPerformanceTestReportDTO uploadPerformanceTestReportDTO){
//        return performanceTestReportService.savePerformanceTestReport(file, uploadPerformanceTestReportDTO);
//    }

    @PostMapping("/uploadMore")
    @ApiModelProperty("批量上传txt文件,保存性能测试报告的数据,仅支持.txt文件")
    public Result uoploadMoreTxt(@RequestParam @ApiParam("需要上传的多个文件") MultipartFile[] files,
                                 @RequestParam  @ApiParam("上传的每个文件对应的场景,0-购买爱盈宝，1-购买月进宝，2-切换tab，" +
                                         "3-浏览资金流水，4-浏览我的资产、优惠券、加息券，5-浏览出借记录") String[] statements,
                                 @RequestParam @ApiParam("启动时间") String startTime,
                                 UploadPerformanceTestReportDTO uploadPerformanceTestReportDTO){
        //当前时间的时间戳，作为redis的key的组成部分
        String time = String.valueOf(System.currentTimeMillis());

        return performanceTestReportService.saveMoreTxtAndStatement(files, statements, startTime, uploadPerformanceTestReportDTO, time);
    }

    //先留着，避免以后用
    public void uploadOneTxt(MultipartFile file, String statement, String time, String startTime){
            performanceTestReportService.saveOneTxtAndStatement(file, statement, time, startTime);
    }

    @PostMapping("/list")
    @ApiModelProperty("获取性能测试报告列表页")
    public Result<ReportListVO> reportList(@RequestBody ReportListDTO reportListDTO){
        return performanceTestReportService.listOfReport(reportListDTO);
    }

    @GetMapping("/delete")
    @ApiModelProperty("根据id，删除某个报告")
    public Result delReport(@ApiParam(required = true, name = "id", value = "性能测试报告id")
                            @RequestParam("id") Integer id){
        return  performanceTestReportService.delReportById(id);
    }

    @PostMapping("modify")
    @ApiModelProperty("编辑性能测试报告对象")
    public Result modifyReport(@RequestBody PerformanceTestReport performanceTestReport){
        return performanceTestReportService.updateReport(performanceTestReport);
    }


//    @PostMapping("/chart")
//    @ApiModelProperty("生成图表数据接口")
//    public Result<List<PerformanceReportVO>> generateChart(GenerateChartDTO generateChartDTO){
//        return performanceTestReportService.generateChart(generateChartDTO);
//    }

    @PostMapping("getPerformanceData")
    @ApiModelProperty("获取性能个测试报告数据")
    public Result<List<PerformanceReportVO>> getPerformanceData(@RequestBody PerformanceTestReport performanceTestReport){
        return performanceTestReportService.getPerformanceData(performanceTestReport);
    }
}
