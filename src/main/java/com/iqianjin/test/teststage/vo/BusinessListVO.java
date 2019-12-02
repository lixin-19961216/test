package com.iqianjin.test.teststage.vo;

import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.entity.BusinessCase;
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
@ApiModel("功能用例对象list")
public class BusinessListVO {

    @ApiModelProperty("功能用例对象list")
    private PageInfo<BusinessCase> list;
}
