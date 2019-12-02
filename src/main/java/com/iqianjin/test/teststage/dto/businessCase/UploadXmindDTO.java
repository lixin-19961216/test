package com.iqianjin.test.teststage.dto.businessCase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("上传xmind文件时对象，包含一些常用属性")
public class UploadXmindDTO {

    @ApiModelProperty(value = "上传人", required = true)
    private String uploadUser;

    @ApiModelProperty(value = "功能用例对应的需求的描述", required = true)
    private String description;

    @ApiModelProperty(value = "需求url")
    private String businessUrl;

    @ApiModelProperty(value = "备注")
    private String remark;
}
