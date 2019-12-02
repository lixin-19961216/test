package com.iqianjin.test.teststage.dto.module;

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
@ApiModel("获取平台&模块列表所需的参数")
public class ModuleListDTO {

    @ApiModelProperty(value = "平台类型,用于搜索", required = false)
    private String platInfo;

    @ApiModelProperty(value = "第几页", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;
}
