package com.iqianjin.test.teststage.dto.tools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("发送加息券时所需的参数，仅支持单个用户发送一个或者多个加息券")
public class SendInterestDTO {
    @ApiModelProperty("对应的红包服务的url")
    private String couponUrl;

    @ApiModelProperty("对应的用户")
    private Long userId;

    @ApiModelProperty("对应的加息券列表")
    private List<Integer> addInterestList;
}
