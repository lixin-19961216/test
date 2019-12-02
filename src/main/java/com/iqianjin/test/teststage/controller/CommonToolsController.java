package com.iqianjin.test.teststage.controller;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.tools.SendInterestDTO;
import com.iqianjin.test.teststage.dto.tools.CreateRedBagDTO;
import com.iqianjin.test.teststage.dto.tools.CreateInterestDTO;
import com.iqianjin.test.teststage.dto.tools.SendRedBagDTO;
import com.iqianjin.test.teststage.service.CommonToolsService;
import com.iqianjin.test.teststage.utils.TransformUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("tools")
@Api("工具类相关接口")
@Slf4j
public class CommonToolsController {

    @Autowired
    CommonToolsService commonToolsService;

    @GetMapping("/encrypt")
    @ApiModelProperty("加密身份证号或电话号接口")
    public Result encryptTool(@RequestParam String content){
        String result = "";
        HashMap<Integer, String> map = new HashMap<>();
        map.put(11, "手机号");
        map.put(16, "银行卡号位数为16");
        map.put(17, "银行卡号位数为17");
        map.put(18, "身份证号");
        map.put(19, "银行卡号位数为19");
        log.info("输入加密信息为{}", content);
        try {
            if (map.containsKey(content.length())) {
                //加密方法用的都是同一个，调一个就行
                result = TransformUtil.encryptMobile(content.toUpperCase());
            } else {
                return Result.failMsg("请输入正确的手机号(11位)或者身份证号(18位)或者银行卡号(16,17,19位)");
            }
            log.info("得到加密结果为：{}", result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success(result);
    }

    @GetMapping("/decrypt")
    @ApiModelProperty("解密身份证号或电话号或银行卡号接口")
    public Result decryptTool(@RequestParam String content){
        String result = "";
        log.info("输入解密信息为{}", content);
        try {
            //解密方法用的都是同一个，调一个就行
            result = TransformUtil.decryptMobile(content);
            log.info("得到解密结果为：{}", result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success(result);
    }

    @PostMapping("/createRedBag")
    @ApiModelProperty("创建固定金额红包和随机红包接口")
    public Result createRedBag(@RequestBody CreateRedBagDTO createRedBagDTO) {
        return commonToolsService.createRedBag(createRedBagDTO);
    }

    @PostMapping("/createInterest")
    @ApiModelProperty("创建加息券接口")
    public Result createInterest (CreateInterestDTO createInterestDTO) {
        return commonToolsService.createInterest(createInterestDTO);
    }

    @PostMapping("/sendRedBag")
    @ApiModelProperty("发送红包接口")
    public Result sendRedBag(@RequestBody SendRedBagDTO sendRedBagDTO) {
        return commonToolsService.sendRedBag(sendRedBagDTO);
    }

    @PostMapping("/sendInterest")
    @ApiModelProperty("发送加息券接口")
    public Result sendInterest(@RequestBody SendInterestDTO sendInterestDTO) {
        return commonToolsService.sendInterest(sendInterestDTO);
    }
}
