package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendAddInterestResDTO {

    @ApiModelProperty(value = "发送成功加息券明细列表")
    private List<FinancePlanAddInterestDetail> details;
}
