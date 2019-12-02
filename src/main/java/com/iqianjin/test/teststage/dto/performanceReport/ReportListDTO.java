package com.iqianjin.test.teststage.dto.performanceReport;

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
public class ReportListDTO {
    @ApiModelProperty(value = "安装包类型,用于搜索,1:Android, 2:IOS")
    private Integer platForm;

    @ApiModelProperty(value = "安装包版本,用于搜索")
    private String version;

    @ApiModelProperty(value = "第几页", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;
}
