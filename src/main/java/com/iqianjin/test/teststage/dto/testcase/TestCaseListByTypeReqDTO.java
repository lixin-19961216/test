package com.iqianjin.test.teststage.dto.testcase;

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
@ApiModel("根据该对象的参数筛选测试用例")
public class TestCaseListByTypeReqDTO {
    @ApiModelProperty(value = "项目名，如：APP,Activity等", required = true)
    private String platInfo;

    @ApiModelProperty(value = "功能模块,用于模糊查询")
    private String featureModule;

    @ApiModelProperty(value = "用例描述,用于模糊查询")
    private String description;

    @ApiModelProperty(value = "标识位 0代表未删除；1代表已删除，前端不展示", required = true)
    private Integer flag;

    @ApiModelProperty(value = "第几页", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;
}
