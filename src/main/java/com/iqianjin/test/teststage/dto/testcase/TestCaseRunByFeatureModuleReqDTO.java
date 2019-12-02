package com.iqianjin.test.teststage.dto.testcase;

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
@ApiModel("按项目类型，运行用例时传递的对象")
public class TestCaseRunByFeatureModuleReqDTO {
    @ApiModelProperty(value = "所要运行的用例的平台类型", required = true)
    private String platform;

    @ApiModelProperty(value = "运行哪个模块", required = true)
    private String FeatureModule;

    @ApiModelProperty(value = "对应用例在哪个项目里运行", required = true)
    private String apiUrl;
}

