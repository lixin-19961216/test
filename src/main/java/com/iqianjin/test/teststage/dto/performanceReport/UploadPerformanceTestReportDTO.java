package com.iqianjin.test.teststage.dto.performanceReport;

import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("上传性能测试报告时所需的参数")
public class UploadPerformanceTestReportDTO {
//    @ApiModelProperty(value = "上传的txt文件", required = true)
//    private MultipartFile file;

    @ApiModelProperty(value = "版本", required = true)
    private String version;

    @ApiModelProperty(value = "安装包类型，1:Android, 2:IOS", required = true)
    private Integer platForm;

    @ApiModelProperty(value = "1:debug包（可切换host）, 2:V3包（不可切换host）, 3:生产包（不可切换host）", required = true)
    private Integer packageType;

    @ApiModelProperty(value = "上传人", required = true)
    private String uploadUser;

    @ApiModelProperty(value = "备注", required = true)
    private String remark;
}
