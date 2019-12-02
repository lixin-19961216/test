package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendRedBagResDTO {
    @ApiModelProperty(value = "发送成功红包明细列表")
    private List<RedbagSendItem> redBagSendItemList;
}
