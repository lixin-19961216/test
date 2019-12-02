package com.iqianjin.test.teststage.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("运行用例后的结果对象")
public class RunTestCaseResultVO {
    @ApiModelProperty("运行的时间,也是一个文件名")
    private String time;

    @ApiModelProperty("运行的成功数")
    private Integer success_count;

    @ApiModelProperty("运行的失败数")
    private Integer fail_count;

    @ApiModelProperty("运行的成功率")
    private Double success_rate;

    @ApiModelProperty("是否有失败的唯一标识，0——无错误，1——有错误，2——不存在该platform下的用例,3——运行异常")
    private String fail_flag;

    @ApiModelProperty("对应的运行报告地址")
    private String reportUrl;
}
