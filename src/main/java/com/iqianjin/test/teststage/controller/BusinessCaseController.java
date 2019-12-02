package com.iqianjin.test.teststage.controller;

import com.github.pagehelper.PageHelper;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.businessCase.BusinessListDTO;
import com.iqianjin.test.teststage.dto.businessCase.UploadXmindDTO;
import com.iqianjin.test.teststage.entity.BusinessCase;
import com.iqianjin.test.teststage.manager.BusinessCaseManager;
import com.iqianjin.test.teststage.service.BusinessCaseService;
import com.iqianjin.test.teststage.vo.BusinessListVO;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("businessCase")
@Api("测试业务时的功能用例相关，为xmind文件")
@Slf4j
public class BusinessCaseController {

    @Autowired
    BusinessCaseService businessCaseService;

    @Autowired
    BusinessCaseManager businessCaseManager;

    @Value("${businessCaseXmind.dir}")
    private String UPLOAD_DIR;

    @PostMapping("upload")
    @ApiModelProperty("上传功能测试用例文件，仅支持xmind文件")
    public Result uploadXmind(@RequestParam("files") MultipartFile file,
                              UploadXmindDTO uploadXmindDTO){
        return businessCaseService.saveBusinessCase(file, uploadXmindDTO);
    }

    @PostMapping("/list")
    @ApiModelProperty("获取功能测试用例列表，可根据需求描述模糊查询")
    public Result<BusinessListVO> businessList(@RequestBody BusinessListDTO businessListDTO){
        PageHelper.startPage(businessListDTO.getPageNum(), businessListDTO.getPageSize());
        if (StringUtils.isNullOrEmpty(businessListDTO.getDescription())){
            return businessCaseService.businessList(businessListDTO);
        }else {
            return businessCaseService.businessListByDescription(businessListDTO);
        }
    }

    @PostMapping("/modify")
    @ApiModelProperty("更新某个功能测试用例信息对象")
    public Result modify(@RequestBody BusinessCase businessCase){
        return businessCaseService.updateBusinessCase(businessCase);
    }

    @GetMapping("/delete")
    @ApiModelProperty("根据id删除某个功能测试用例信息对象")
    public Result delete(@RequestParam Integer id){
        return businessCaseService.deleteBusinessCase(id);
    }

    @GetMapping("/download")
    @ApiModelProperty("根据id下载某个功能测试用例对应的文件")
    public Result download(@RequestParam Integer id, HttpServletResponse response){
        try {
            businessCaseService.downloadBusinessCase(id, response);
        }catch (IOException e){
            log.info("下载异常{}", e);
            e.printStackTrace();
            return Result.failMsg("下载失败");
        }
        return Result.success("下载成功");
    }
}
