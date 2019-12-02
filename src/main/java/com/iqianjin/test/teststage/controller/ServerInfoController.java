package com.iqianjin.test.teststage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.serverInfo.ServerInfoListDTO;
import com.iqianjin.test.teststage.dto.serverInfo.ServerInfoSaveDTO;
import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.service.ServerInfoService;
import com.iqianjin.test.teststage.vo.ServerInfoListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("serverInfo")
@Api("运行用例时的服务信息")
@Slf4j
public class ServerInfoController {

    @Autowired
    ServerInfoService serverInfoService;

    @PostMapping("/list")
    @ApiOperation("获取所有服务的信息")
    public Result<ServerInfoListVO> serverInfoList(@RequestBody ServerInfoListDTO serverInfoListDTO){
        return serverInfoService.findAllServerInfo(serverInfoListDTO);
    }

    @PostMapping("/add")
    @ApiOperation("新增一个接口请求时的ip信息")
    public Result addServerInfo(@RequestBody ServerInfoSaveDTO serverInfoSaveDTO){
        return serverInfoService.saveServerInfo(serverInfoSaveDTO);
    }

    @PostMapping("/modify")
    @ApiOperation("编辑一个已有的接口请求时的ip信息")
    public Result modifyServerInfo(@RequestBody ServerInfo serverInfo){
        return serverInfoService.updateServerInfo(serverInfo);
    }

    @GetMapping("/delete")
    @ApiOperation("根据id删除已有的接口请求时的ip信息")
    public Result deleteServerInfo(@RequestParam Integer id){
        return serverInfoService.deleteServerInfoById(id);
    }

    @GetMapping("/getAllServerInfo")
    @ApiModelProperty("获取所有的平台distinct")
    public Result getAllServerInfo(){
        return serverInfoService.getAllServerInfo();
    }
}
