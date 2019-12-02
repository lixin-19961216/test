package com.iqianjin.test.teststage.dto.testcase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("删除用例时传递的对象")
public class TestCaseDelReqDTO {
    @ApiModelProperty(value = "删除用例时需要的id", required = true)
    private Integer id;
}
