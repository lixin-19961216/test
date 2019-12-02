package com.iqianjin.test.teststage.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@ApiModel(description = "Jenkins执行接口测试时的响应对象")
public class JenkinsResult<T> implements Serializable {

    private static final long serialVersionUID = -6993613007033583204L;

    public static final Integer SUCCESS = 1;
    public static final Integer FAILED = -1;

    @ApiModelProperty(value = "成功率")
    private Double successRate;

    @ApiModelProperty(value = "响应code:1处理成功;其他值失败", required = true)
    private Integer code;

    @ApiModelProperty(value = "响应提示信息")
    private String msg;

    @ApiModelProperty(value = "报告url")
    private String reportUrl;

    private JenkinsResult() {
    }

    public static JenkinsResult builder() {
        return new JenkinsResult();
    }

    public JenkinsResult<T> successRate(Double successRate) {
        this.successRate = successRate;
        return this;
    }

    public JenkinsResult<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public JenkinsResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public JenkinsResult<T> reportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
        return this;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getReportUrl() {
        return this.reportUrl;
    }


    public JenkinsResult<T> setSuccessRate(Double successRate) {
        this.successRate = successRate;
        return this;
    }

    public JenkinsResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public JenkinsResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public JenkinsResult<T> setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
        return this;
    }

    @ApiModelProperty(value = "响应是否正常:true正常;false异常")
    public boolean isSuccess() {
        return Objects.equals(SUCCESS, this.code);
    }

}
