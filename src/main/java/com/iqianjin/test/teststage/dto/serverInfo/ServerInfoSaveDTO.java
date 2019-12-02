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
@ApiModel("保存接口请求所需要的服务的ip或域名")
public class ServerInfoSaveDTO {
    @ApiModelProperty(value = "接口请求ip", required = true)
    private String apiUrl;

    @ApiModelProperty(value = "对应接口请求ip的描述", required = true)
    private String description;
}
