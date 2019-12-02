package com.iqianjin.test.teststage.dto.excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("上传的excel")
public class UploadExcelReqDTO {

    @ApiModelProperty(value = "上传的excel文件", required = true)
    private MultipartFile file;
}
