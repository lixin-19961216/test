package com.iqianjin.test.teststage.dto.tools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
public class CreateRedBagDTO {

    @ApiModelProperty("在哪个数据库添加")
    private String dataHost;

    @ApiModelProperty("数据库用户名")
    private String dataUseName;

    @ApiModelProperty("数据库密码")
    private String dataPassword;

    @ApiModelProperty("红包名称")
    @NotNull(message = "红包名称不能为空")
    private String redbagName;

    @ApiModelProperty("红包面值")
    @NotNull(message = "红包面值不能为空")
    private Double redbagPar;

    @ApiModelProperty("红包类型，1：固定金额；2：随机金额；3：百分比金额")
    @NotNull(message = "红包类型不能为空")
    private Integer redbagParType;

    @ApiModelProperty("随机金额、百分比金额的金额规则")
    private String redbagParRule;

    @ApiModelProperty("红包创建人")
    private String redbagCreateUser;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("红包类型，1，普通红包2，满减可叠加使用红包3，满减不可叠加红包")
    private Integer redbagType;

    @ApiModelProperty("满额可用")
    private Double quota;
}
