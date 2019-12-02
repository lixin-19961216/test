package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendAddInterestReqDTO {

    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "加息券Id列表")
    @NotEmpty(message = "加息券Id列表不能为空")
    private List<Integer> addInterestIdList;

    @ApiModelProperty(value = "发放加息券业务Id")
    @NotBlank(message = "发放加息券业务Id不能为空")
    private String bizId;
}
