package com.iqianjin.test.teststage.vo;

import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.entity.AppPackage;
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
@ApiModel("运行接口测试用例时选择的服务对象list")
public class AppPackageListVO {
    @ApiModelProperty("已有的所有服务list")
    private PageInfo<AppPackage> AppPackageList;
}
