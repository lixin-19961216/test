package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@ApiModel(description = "响应对象")
public class CouponResult<T> implements Serializable {

    private static final long serialVersionUID = -6993613007033583204L;

    public static final Integer SUCCESS = 1;
    public static final Integer FAILED = -1;

    @ApiModelProperty(value = "响应code:1处理成功;其他值失败", required = true)
    private Integer code;

    @ApiModelProperty(value = "响应提示信息")
    private String msg;

    @ApiModelProperty(value = "响应body内容")
    private T data;

    private CouponResult() {
    }

    public static CouponResult builder() {
        return new CouponResult();
    }

    public static <T> CouponResult<T> successMsg(String msg) {
        return builder().code(SUCCESS).msg(msg);
    }

    public static <T> CouponResult<T> success(T data) {
        return builder().code(SUCCESS).data(data);
    }

    public static <T> CouponResult<T> failMsg(String msg) {
        return builder().code(FAILED).msg(msg);
    }

    public static <T> CouponResult<T> fail(int code, String msg) {
        return builder().code(code).msg(msg);
    }

    public static <T> CouponResult<T> success() {
        return successMsg("成功");
    }

    public CouponResult<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public CouponResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public CouponResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public CouponResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public CouponResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public CouponResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @ApiModelProperty(value = "响应是否正常:true正常;false异常")
    public boolean isSuccess() {
        return Objects.equals(SUCCESS, this.code);
    }

}
