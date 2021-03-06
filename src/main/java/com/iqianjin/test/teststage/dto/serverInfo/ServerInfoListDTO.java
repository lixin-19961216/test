package com.iqianjin.test.teststage.dto.serverInfo;

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
@ApiModel("获取接口请求域名或者ip所需的参数")
public class ServerInfoListDTO {
    @ApiModelProperty(value = "第几页", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;
}
