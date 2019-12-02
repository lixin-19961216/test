package com.iqianjin.test.teststage.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health/check")
    @ApiOperation("健康检查")
    public String healthCheck() {
        return "ok";
    }
}
