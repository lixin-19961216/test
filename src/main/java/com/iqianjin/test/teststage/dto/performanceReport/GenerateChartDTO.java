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
@ApiModel("生成测试图表所需的参数")
public class GenerateChartDTO {
    @ApiModelProperty(value = "单个报告生成图表")
    private Integer id;

    @ApiModelProperty(value = "出现此参数，则需要生成两个对比的图表")
    private Integer comparedId;
}
