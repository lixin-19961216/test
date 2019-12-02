package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * 接口请求入参公共基类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "接口公共请求参数")
public class CouponHeader {

    @ApiModelProperty(value = "客户端应用编码", required = true, dataType = "String", example = "iqianjin")
    @NotEmpty(message = "客户端应用编码不能为空")
    private String appCode;

}
