package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * 统一接收请求参数对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "统一请求参数")
public class CouponRequest<T> {

    private CouponHeader header;

    private T body;

}