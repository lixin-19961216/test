package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.performanceReport.GenerateChartDTO;
import com.iqianjin.test.teststage.dto.performanceReport.ReportListDTO;
import com.iqianjin.test.teststage.dto.performanceReport.UploadPerformanceTestReportDTO;
import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import com.iqianjin.test.teststage.vo.PerformanceReportVO;
import com.iqianjin.test.teststage.vo.ReportListVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface PerformanceTestReportService {
//    /**
//     * 保存上传的测试报告文件信息
//     * @param file
//     * @param uploadPerformanceTestReportDTO
//     * @return
//     */
//    Result savePerformanceTestReport(MultipartFile file, UploadPerformanceTestReportDTO uploadPerformanceTestReportDTO);

    /**
     * 根据安装包类型和版本查找性能测试报告信息
     * @param reportListDTO
     * @return
     */
    Result<ReportListVO> listOfReport(ReportListDTO reportListDTO);

    /**
     * 根据id删除指定性能测试报告信息
     * @param id
     * @return
     */
    Result delReportById (Integer id);

//    /**
//     * 根据路径在服务器上寻找对应报告，并计算返回
//     * @param filePath
//     * @return
//     */
//    PerformanceReportVO chart(String filePath);

    /**
     * 编辑性能测试报告对象信息
     * @param performanceTestReport
     */
    Result updateReport(PerformanceTestReport performanceTestReport);

//    /**
//     * 生成图表对应信息
//     * @param generateChartDTO
//     * @return
//     */
//    Result generateChart(GenerateChartDTO generateChartDTO);

    /**
     * 批量上传&保存结果
     * @param files
     * @param statements
     * @param startTime
     * @param uploadPerformanceTestReportDTO
     * @return
     */
    Result saveMoreTxtAndStatement(MultipartFile[] files, String[] statements, String startTime,
                                   UploadPerformanceTestReportDTO uploadPerformanceTestReportDTO, String time);

    /**
     *保存单个txt文件计算后的结果内容到reids
     * @param file
     * @param statement
     * @param time
     */
    void saveOneTxtAndStatement(MultipartFile file, String statement, String time, String startTime);

    /**
     *
     * @param performanceTestReport
     * @return
     */
    Result<List<PerformanceReportVO>> getPerformanceData(PerformanceTestReport performanceTestReport);
}
