package com.iqianjin.test.teststage.vo;

import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.entity.TestCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("接口测试用例对象list")
public class TestCaseListVO {
    @ApiModelProperty("用例对象list")
    private PageInfo<TestCase> testCaseList;
}
