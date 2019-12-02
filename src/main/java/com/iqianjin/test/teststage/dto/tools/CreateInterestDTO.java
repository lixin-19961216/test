package com.iqianjin.test.teststage.dto.tools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("创建红包或者加息券是所需的参数")
public class CreateInterestDTO {
    @ApiModelProperty("在哪个数据库添加")
    private String dataHost;

    @ApiModelProperty("数据库用户名")
    private String dataUseName;

    @ApiModelProperty("数据库密码")
    private String dataPassword;

    @ApiModelProperty("加息券名称")
    @NotNull(message = "加息券名称不能为空")
    private String interestName;

    @ApiModelProperty("加息券类型，1：3月期整存宝+   2：6月期整存宝+  3：12月期整存宝+  4：整存宝+通用  5：爱月投 6：爱活宝'")
    @NotNull(message = "加息券类型不能为空")
    private Integer interestType;

    @ApiModelProperty("加息额度")
    @NotNull(message = "加息额度不能为空")
    private Double interestLimit;

    @ApiModelProperty("加息券有效期")
    private Integer interestValidityDate;

    @ApiModelProperty("加息天数")
    private Integer interestContinueDay;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("加息特性：1:普通 2、可叠加")
    private Integer interestFeature;

    @ApiModelProperty("满减额度")
    private Double quota;

    @ApiModelProperty("加息券类型 1：普通 2：满减")
    private Integer interestCondition;

    @ApiModelProperty("指定理财计划期数")
    private Integer planPeriod;

    @ApiModelProperty("适用产品范围:2爱盈宝;10月进宝")
    private Integer productScope;
}
