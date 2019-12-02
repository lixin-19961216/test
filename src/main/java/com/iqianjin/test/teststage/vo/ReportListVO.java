package com.iqianjin.test.teststage.vo;

import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("平台&模块对应关系列表")
public class ReportListVO {
    @ApiModelProperty(value = "报告对象List")
    private PageInfo<PerformanceTestReport> reportList;
}
