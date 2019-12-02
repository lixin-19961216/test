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
@ApiModel("发送红包时所需的参数，仅支持单个用户发送一个或者多个红包")
public class SendRedBagDTO {
    @ApiModelProperty("对应的红包服务的url")
    private String couponUrl;

    @ApiModelProperty("对应的用户")
    private Long userId;

    @ApiModelProperty("对应的红包列表")
    private List<Integer> redBagIdList;
}
