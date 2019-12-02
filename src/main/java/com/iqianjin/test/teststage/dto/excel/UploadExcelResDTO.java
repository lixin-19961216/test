package com.iqianjin.test.teststage.dto.excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("上传excel后返回结果")
public class UploadExcelResDTO {

    @ApiModelProperty("上传excel后返回结果信息")
    private String msg;
}
