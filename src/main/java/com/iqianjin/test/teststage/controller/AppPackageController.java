package com.iqianjin.test.teststage.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.AppPackageListDTO;
import com.iqianjin.test.teststage.entity.AppPackage;
import com.iqianjin.test.teststage.service.AppPackageService;
import com.iqianjin.test.teststage.utils.QrCodeUtils;
import com.iqianjin.test.teststage.vo.AppPackageListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@RestController
@RequestMapping("/appPackage")
@Api("APP包管理")
@Slf4j
public class AppPackageController {

    @Value("${apackage.dir}")
    private String aPackage_DIR;

    @Autowired
    private AppPackageService appPackageService;

    /**
     * 上传文件接口，目前支持apk&ipa格式
     *
     * @param //file
     * @return
     */

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("上传app包")
    public Result upload(@RequestParam("file") MultipartFile file,
                         AppPackage appPackage) {
        return appPackageService.uploadAppPackage(file, appPackage);
    }

    /**
     * 获取安装包信息&模糊查询
     *
     * @return
     */

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation("获取所有的安装包信息")
    public Result<AppPackageListVO> packageList(AppPackageListDTO appPackageListDTO) {
        if (appPackageListDTO.getPageNum()!=null&&appPackageListDTO.getPageSize()!=null){
            PageHelper.startPage(appPackageListDTO.getPageNum(), appPackageListDTO.getPageSize());
        }
        AppPackageListVO appPackageListVO = new AppPackageListVO();
        try {
            List<AppPackage> list = appPackageService.findByParams(appPackageListDTO);
            PageInfo<AppPackage> pageInfo = new PageInfo<>(list);
            appPackageListVO.setAppPackageList(pageInfo);
            log.info("查找APP包{}", list);
            return Result.success(appPackageListVO);
        } catch (Exception e) {
            log.info("查找APP包失败");
            e.printStackTrace();
            return Result.failMsg("查找APP包失败");
        }
    }

    /**
     * 删除包，根据id
     *
     * @return
     */

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation("删除app记录")
    public Result delPackage(@RequestParam(value = "id") Integer id) {
        return appPackageService.updateFlagDelete(id);
    }

    /**
     * 下载安装包
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation("下载指定app")
    public void downloadPackage(@RequestParam(value = "id") Integer id,
                                HttpServletResponse response) {
        appPackageService.downloadAppPackage(id, response);
    }

    /**
     * 二维码
     *
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "/qrCode",method = RequestMethod.GET)
    @ApiOperation("生成app下载二维码")
    public void qrCode(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") Integer id) {
        StringBuffer url = request.getRequestURL();
        log.info("之前的url{}", url);
//        String requestUrl = url.toString().replace("qrCode", "download");
        //先写死，部署在服务器后，不知道啥原因缺个端口号
        String requestUrl = "http://10.10.201.39:8870/newTestStage/appPackage/download";
        requestUrl = requestUrl + "?id=" + id;
        log.info("拼接后的url{}", requestUrl);
        try {
            OutputStream os = response.getOutputStream();
            QrCodeUtils.encode(requestUrl, "/static/images/logo.png", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

