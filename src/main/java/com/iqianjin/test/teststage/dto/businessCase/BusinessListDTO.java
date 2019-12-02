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
@ApiModel("查询功能用例列表时所需的参数")
public class BusinessListDTO {
    @ApiModelProperty(value = "需求描述,用于模糊搜索")
    private String description;

    @ApiModelProperty(value = "第几页", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;
}
