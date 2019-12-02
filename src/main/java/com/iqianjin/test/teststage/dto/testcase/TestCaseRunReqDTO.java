package com.iqianjin.test.teststage.dto.testcase;
import com.iqianjin.test.teststage.entity.ServerInfo;
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
@ApiModel("运行用例时传递的对象")
public class TestCaseRunReqDTO {
    @ApiModelProperty(value = "所要运行的用例的id list", required = true)
    private List<Integer> ids;

    @ApiModelProperty(value = "对应用例在哪个项目里运行", required = true)
    private String apiUrl;
}
