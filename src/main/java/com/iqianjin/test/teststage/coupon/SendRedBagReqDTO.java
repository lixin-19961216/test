package com.iqianjin.test.teststage.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(description = "给单个用户发放多个红包的请求参数")
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendRedBagReqDTO {

    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "红包发放Id集合")
    @NotEmpty(message = "红包Id集合不能为空")
    private List<Integer> redBagIdList;

    @ApiModelProperty(value = "发放红包业务Id")
    @NotBlank(message = "发放红包业务Id不能为空")
    private String bizId;

    @ApiModelProperty(value = "购买金额(发放比例红包时需要)")
    private Double buyAmount;

    @ApiModelProperty(value = "红包生效时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date effectiveTime;
}
