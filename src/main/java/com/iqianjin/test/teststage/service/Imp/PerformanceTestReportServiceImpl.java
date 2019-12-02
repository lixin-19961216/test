package com.iqianjin.test.teststage.service.Imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.performanceReport.GenerateChartDTO;
import com.iqianjin.test.teststage.dto.performanceReport.ReportListDTO;
import com.iqianjin.test.teststage.dto.performanceReport.UploadPerformanceTestReportDTO;
import com.iqianjin.test.teststage.entity.PerformanceTestReport;
import com.iqianjin.test.teststage.manager.PerformanceTestReportManager;
import com.iqianjin.test.teststage.service.PerformanceTestReportService;
import com.iqianjin.test.teststage.utils.RedisUtils;
import com.iqianjin.test.teststage.vo.PerformanceReportVO;
import com.iqianjin.test.teststage.vo.ReportListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class PerformanceTestReportServiceImpl implements PerformanceTestReportService {

    @Autowired
    PerformanceTestReportManager performanceTestReportManager;

    private static final long MAX_FILE_SIZE = 100*1024*1024;

    @Value("${performanceTestReport.dir}")
    private String  UPLOAD_DIR;

//    @Override
//    public Result savePerformanceTestReport(MultipartFile file, UploadPerformanceTestReportDTO uploadPerformanceTestReportDTO) {
//        log.info("测试报告数据文件上传开始");
//        PerformanceTestReport performanceTestReport = new PerformanceTestReport();
//
//        if (!file.isEmpty()){
//            String originFileName = file.getOriginalFilename();
//            String fileNameNoEx = originFileName.substring(0, originFileName.lastIndexOf("."));
//            String extension = originFileName.substring(originFileName.lastIndexOf("."));
//            long size = file.getSize();
//            String uploadTime = String.valueOf(System.currentTimeMillis());
//            String newFileName = fileNameNoEx + uploadTime + extension;
//
//            //判断上传文件类型
//            if (!extension.contains("txt")){
//                return Result.failMsg("文件类型错误，目前只支持txt文件");
//            }
//            if (size == 0){
//                return Result.failMsg("上传文件不能为空");
//            }
//            if (size > MAX_FILE_SIZE){
//                return Result.failMsg("上传文件过大");
//            }
//
//            performanceTestReport.setCreateDate(new Date());
//            performanceTestReport.setFlag(0);
//            performanceTestReport.setFileName(newFileName);
//            performanceTestReport.setVersion(uploadPerformanceTestReportDTO.getVersion());
//            performanceTestReport.setPlatForm(uploadPerformanceTestReportDTO.getPlatForm());
//            performanceTestReport.setPackageType(uploadPerformanceTestReportDTO.getPackageType());
//            performanceTestReport.setRemark(uploadPerformanceTestReportDTO.getRemark());
//            performanceTestReport.setUploadUser(uploadPerformanceTestReportDTO.getUploadUser());
//
//            //为了防止名字重复，造成后续在服务器上找文件时冲突，加上时间戳
//            File newFile = new File(UPLOAD_DIR, newFileName);
//            log.info("原来的文件名{}, 保存在服务器上的文件名{}", file.getName(), newFileName);
//            try {
//                newFile.createNewFile();
//                FileOutputStream fos = new FileOutputStream(newFile);
//                fos.write(file.getBytes());
//                fos.close();
//            }catch (Exception e){
//                log.info("将文件保存到服务器上时失败");
//                e.printStackTrace();
//                return Result.failMsg("保存上传文件失败，请重试！");
//            }
//        }
//        //保存到数据库
//        try {
//            performanceTestReportManager.savePerformanceTestReport(performanceTestReport);
//            log.info("保存performanceTestReport对象信息为：{}", performanceTestReport);
//        }catch (Exception e){
//            log.info("保存上传txt文件相关信息失败，请重试");
//            e.printStackTrace();
//            return Result.failMsg("上传失败，请重试！");
//        }
//        return Result.success("文件上传成功！");
//    }

    @Override
    public Result<ReportListVO> listOfReport(ReportListDTO reportListDTO) {
        PageHelper.startPage(reportListDTO.getPageNum(), reportListDTO.getPageSize());
        ReportListVO reportListVO = new ReportListVO();
        List<PerformanceTestReport> list = new ArrayList<>();
        try {
            if (!StringUtils.isEmpty(reportListDTO.getPlatForm())){
                if (!StringUtils.isEmpty(reportListDTO.getVersion())){
                    log.info("传入了platForm和version两个字段，{}, {}", reportListDTO.getPlatForm(), reportListDTO.getVersion());
                    list = performanceTestReportManager.findReportByPlatFormAndVersion(reportListDTO.getPlatForm(), reportListDTO.getVersion());
                }else {
                    log.info("传入了platForm一个字段，{}", reportListDTO.getPlatForm());
                    list = performanceTestReportManager.findReportByPlatForm(reportListDTO.getPlatForm());
                }
            }else {
                log.info("传入了version一个字段，{}", reportListDTO.getVersion());
                if (!StringUtils.isEmpty(reportListDTO.getVersion())){
                    list = performanceTestReportManager.findReportByVersion(reportListDTO.getVersion());
                }else {
                    log.info("一个字段没有传入，默认搜索全部");
                    list = performanceTestReportManager.findAll();
                }
            }
            PageInfo<PerformanceTestReport> pageInfo = new PageInfo<>(list);
            reportListVO.setReportList(pageInfo);
        }catch (Exception e){
            log.info("查询性能测试报告列表页失败");
            e.printStackTrace();
            return Result.failMsg("获取列表页失败！");
        }
        return Result.success(reportListVO);
    }

    @Override
    public Result delReportById(Integer id) {
        PerformanceTestReport performanceTestReport = performanceTestReportManager.findReportById(id);
        String key = "Performance:" + performanceTestReport.getFileName().split("-")[0] + ":"
                + performanceTestReport.getFileName().split("-")[1];
        try {
            log.info("开始删除对应redis中的key{}", key);
            RedisUtils.delRedisByKey(key);
            log.info("删除key{}成功", key);
        }catch (Exception e){
            log.info("删除key失败{},redis中无此key", key);
            e.printStackTrace();
        }
        try {
            log.info("开始删除id为{}的性能测试报告对象信息！");
            performanceTestReportManager.delReportById(id);
            log.info("删除成功！");
        }catch (Exception e){
            log.info("删除失败，id为{}", id);
            e.printStackTrace();
            return Result.failMsg("删除失败!");
        }
        return Result.success("删除成功！");
    }

//    @Override
//    public PerformanceReportVO chart(String filePath){
//
//        String txtStr = reader(filePath);
//
//        if (txtStr != null) {
//            return process(txtStr);
//        } else {
//            return null;
//        }
//    }

    @Override
    public Result updateReport(PerformanceTestReport performanceTestReport) {
        log.info("开始编辑{}", performanceTestReport.getRemark());
        try {
            performanceTestReportManager.updateReport(performanceTestReport);
            log.info("编辑成功{}", performanceTestReport.getRemark());
        }catch (Exception e){
            log.info("编辑失败{}", performanceTestReport.getRemark());
            e.printStackTrace();
            return Result.failMsg("编辑失败");
        }
        return Result.success("编辑成功");
    }

//    @Override
//    public Result generateChart(GenerateChartDTO generateChartDTO) {
//        List<PerformanceReportVO> list = new ArrayList<>();
//        if (generateChartDTO.getComparedId() == 0){
//            log.info("生成单个图表");
//            PerformanceTestReport performanceTestReport = performanceTestReportManager.findReportById(generateChartDTO.getId());
//
//            String fileName = performanceTestReport.getFileName();
//            String filePath = UPLOAD_DIR + fileName;
//
//            try {
//                log.info("开始计算报告{}中各个数据{}", performanceTestReport.getRemark(),System.currentTimeMillis());
//                list.add(this.chart(filePath));
//                log.info("{}报告中各个数据计算完成{}", performanceTestReport.getRemark(), System.currentTimeMillis());
//            }catch (Exception e){
//                log.info("计算报告中数据失败{}", generateChartDTO.getId());
//                e.printStackTrace();
//                return Result.failMsg("计算数据失败！");
//            }
//        }else {
//            log.info("生成两个对比图表");
//            PerformanceTestReport performanceTestReport = performanceTestReportManager.findReportById(generateChartDTO.getId());
//            PerformanceTestReport performanceTestReport1 = performanceTestReportManager.findReportById(generateChartDTO.getComparedId());
//
//            String fileName = performanceTestReport.getFileName();
//            String fileName1 = performanceTestReport1.getFileName();
//            String filePath = UPLOAD_DIR + fileName;
//            String filePath1 = UPLOAD_DIR + fileName;
//
//            try {
//                log.info("开始计算报告中各个数据{},第一个，id为{}", System.currentTimeMillis(), generateChartDTO.getId());
//                list.add(this.chart(filePath));
//                log.info("第一个报告中各个数据计算完成{}", System.currentTimeMillis());
//
//                log.info("开始计算报告中各个数据{},第一个，id为{}", System.currentTimeMillis(), generateChartDTO.getId());
//                list.add(this.chart(filePath1));
//                log.info("第二个报告中各个数据计算完成{}", System.currentTimeMillis());
//            }catch (Exception e){
//                log.info("计算报告中数据失败{}和对比项{}", performanceTestReport.getRemark(), performanceTestReport1.getRemark());
//                e.printStackTrace();
//                return Result.failMsg("计算数据失败！");
//            }
//        }
//        return Result.success(list);
//    }

    @Override
    public Result saveMoreTxtAndStatement(MultipartFile[] files, String[] statements, String startTime,
                                          UploadPerformanceTestReportDTO uploadPerformanceTestReportDTO, String time) {
        log.info("测试报告数据文件上传开始");
        PerformanceTestReport performanceTestReport = new PerformanceTestReport();

        if (files.length != statements.length){
            return Result.failMsg("文件数和场景数对应不上，请重新上传");
        }

        for (int i = 0; i < files.length; i++){
            if (!files[i].isEmpty()){
                String originFileName = files[i].getOriginalFilename();
                String fileNameNoEx = originFileName.substring(0, originFileName.lastIndexOf("."));
                String extension = originFileName.substring(originFileName.lastIndexOf("."));
                long size = files[i].getSize();
                if (!extension.contains("txt")){
                    return Result.failMsg("文件类型错误，目前只支持txt文件");
                }
                if (size == 0){
                    return Result.failMsg("上传文件不能为空");
                }
                if (size > MAX_FILE_SIZE){
                    return Result.failMsg("上传文件过大");
                }
                try {
                    log.info("开始保存第{}个文件{},对应的场景为{}", i, files[i].getOriginalFilename(), statements[i]);
                    this.saveOneTxtAndStatement(files[i], statements[i], time, startTime);
                }catch (Exception e){
                    log.info("保存第{}个文件{},对应的场景为{}失败", i, files[i].getOriginalFilename(), statements[i]);
                    e.printStackTrace();
                    return Result.failMsg("上传失败，请重试！");
                }
                log.info("保存所有文件内容到redis成功");
            }
        }
        String temp = "";
        for (String statement : statements){
            temp = temp + "-" +statement;
        }
        performanceTestReport.setCreateDate(new Date());
        performanceTestReport.setFlag(0);
        performanceTestReport.setFileName(time + temp);//这样保存，分解后可作为redis的key
        performanceTestReport.setVersion(uploadPerformanceTestReportDTO.getVersion());
        performanceTestReport.setPlatForm(uploadPerformanceTestReportDTO.getPlatForm());
        performanceTestReport.setPackageType(uploadPerformanceTestReportDTO.getPackageType());
        performanceTestReport.setRemark(uploadPerformanceTestReportDTO.getRemark());
        performanceTestReport.setUploadUser(uploadPerformanceTestReportDTO.getUploadUser());

        try {
            performanceTestReportManager.savePerformanceTestReport(performanceTestReport);
            log.info("保存performanceTestReport对象信息为：{}", performanceTestReport);
        }catch (Exception e){
            log.info("保存上传txt文件相关信息失败，请重试");
            e.printStackTrace();
            return Result.failMsg("上传失败，请重试！");
        }
        return Result.success("文件上传成功！");
    }

    @Override
    public void saveOneTxtAndStatement(MultipartFile file, String statement, String time, String startTime) {
        PerformanceReportVO result = this.chartData(file, startTime, statement);
        //将计算结果永久存在redis缓存里
        Object object = JSONArray.toJSON(result);
        String key = String.format("Performance:%s:%s", time, statement);
        String value = object.toString();
        RedisUtils.setRedisValueByKeyForever(key, value);
    }

    @Override
    public Result<List<PerformanceReportVO>> getPerformanceData(PerformanceTestReport performanceTestReport) {
        List<PerformanceReportVO> list = new ArrayList<>();

        //之前上传时将fileName分解后作为key
        String fileName = performanceTestReport.getFileName();
        String[] s = fileName.split("-");
        String[] result = new String[s.length - 1];
        PerformanceReportVO[] reportVOS = new PerformanceReportVO[result.length];
        for (int i = 1; i < s.length; i++){
            String key = "Performance:" + s[0] + ":" + s[i];
            log.info("开始查找key：{}", key);
            try {
                result[i - 1] = RedisUtils.getRedisValueByKey(key);
            }catch (Exception e){
                log.info("查找key：{}失败", key);
                e.printStackTrace();
                return Result.failMsg("查找数据失败，请重试！");
            }
        }
        for (int i = 0; i < result.length; i++){
            log.info("开始将redis的value转换为PerformanceReportVO对象");
            try {
                reportVOS[i] = JSON.parseObject(result[i], PerformanceReportVO.class);
                log.info("开始set List, value = {}", reportVOS[i]);
                list.add(reportVOS[i]);
            }catch (Exception e){
                log.info("转换对象出错");
                e.printStackTrace();
                return Result.failMsg("查找数据失败，请重试！");
            }
        }
        return Result.success(list).setMsg("查找成功");
    }

    public PerformanceReportVO chartData(MultipartFile file, String startTime, String statement){

        String txtStr = readerMultipartFile(file);

        if (txtStr != null) {
            return process(txtStr, startTime, statement);
        } else {
            return null;
        }
    }

//    private String reader(String filePath) {
//        try {
//            File file = new File(filePath);
//            if (file.isFile() && file.exists()) {
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(read);
//                //readline只能度一行，读全部加循环
//                //String lineTxt = bufferedReader.readLine();
//                String line;
//                String lineTxt = "";
//                while((line = bufferedReader.readLine()) != null){
//                    lineTxt += line;
//                }
//                while (lineTxt != "") {
//                    return lineTxt;
//                }
//            }
//        } catch (UnsupportedEncodingException | FileNotFoundException e) {
//            System.out.println("Cannot find the file specified!");
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.out.println("Error reading file content!");
//            e.printStackTrace();
//        }
//        return null;
//    }

    private String readerMultipartFile(MultipartFile file1) {
        try {
            File file = new File("/data/other/1.txt");
            FileUtils.copyInputStreamToFile(file1.getInputStream(), file);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                //readline只能度一行，读全部加循环
                //String lineTxt = bufferedReader.readLine();
                String line;
                String lineTxt = "";
                while((line = bufferedReader.readLine()) != null){
                    lineTxt += line;
                }
                while (lineTxt != "") {
                    return lineTxt;
                }
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.out.println("Cannot find the file specified!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file content!");
            e.printStackTrace();
        }
        return null;
    }

    private PerformanceReportVO process(String txtStr, String startTime, String statement) {

        PerformanceReportVO report1 = JSON.parseObject(txtStr, PerformanceReportVO.class);
        report1.setStartTime(startTime);
        //根据code获取对应的场景，并写入对象的属性中
        report1.setStatement(this.getStatementByCode(statement));

        Double [] cpuArray = new Double[report1.getCommonData().size() + 1];
        Double [] fpsArray = new Double[report1.getCommonData().size() + 1];
        Double [] memoryArray = new Double[report1.getCommonData().size() + 1];
        Double [] upFlowArray = new Double[report1.getFlowData().size() + 1];
        Double [] downFlowArray = new Double[report1.getFlowData().size() + 1];

        for (int i = 0; i < report1.getCommonData().size(); i++){
            cpuArray[i] = report1.getCommonData().get(i).getCpu();
            fpsArray[i] = report1.getCommonData().get(i).getFps();
            memoryArray[i] = report1.getCommonData().get(i).getMemory();
        }

        //获取上行和下行流量
        for (int i = 0; i < report1.getFlowData().size(); i++){
            upFlowArray[i] = report1.getFlowData().get(i).getUpFlow();
            downFlowArray[i] = report1.getFlowData().get(i).getDownFlow();
        }

        //获取上下行流量最高峰值对应的url名
        String maxUpFlowUrl = "";
        String maxDownFlowUrl = "";
        double maxUpFlow = 0;
        double maxDownFlow = 0;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < (upFlowArray.length - 1); i++){
            if(upFlowArray[i] > maxUpFlow){
                maxUpFlow = upFlowArray[i];
                index1 = i;
            }
        }
        for (int i = 0; i < (downFlowArray.length - 1); i++){
            if (downFlowArray[i] > maxDownFlow){
                maxDownFlow = downFlowArray[i];
                index2 = i;
            }
        }
        maxUpFlowUrl = report1.getFlowData().get(index1).getUrl();
        maxDownFlowUrl = report1.getFlowData().get(index2).getUrl();
        report1.setMaxUpFlowUrl(maxUpFlowUrl);
        report1.setMaxDownFlowUrl(maxDownFlowUrl);

        //获取前台平均cpu和最高cpu和最低cpu
        double avrCPU = 0;
        double totalCPU = 0;
        double maxCPU = 0;
        double minCPU = 0;
        for (int i = 0; i < (cpuArray.length - 1); i++){
            Double a = cpuArray[i];
            Double b = cpuArray[i];
            totalCPU += a;
            if(maxCPU < a){
                double temp = maxCPU;
                maxCPU = a;
                a = temp;
            }
            if(minCPU > b){
                double temp = minCPU;
                minCPU = b;
                b = temp;
            }
        }
        avrCPU = totalCPU / (cpuArray.length - 1);

        //获取前台平均内存和最高内存和最低内存
        double avrMemory = 0;
        double totalMemory = 0;
        double maxMemory =0;
        double minMemory =0;
        for (int i = 0; i < (memoryArray.length - 1); i++){
            Double a = memoryArray[i];
            totalMemory += a;
            if(maxMemory < a){
                maxMemory = a;
            }
            if(minMemory > a){
                minMemory = a;
            }
        }
        avrMemory = totalMemory / (memoryArray.length - 1);

        //获取上下行总流量,上下行流量的最大值和最小值
        double totalUp = 0;
        double totalDown = 0;
        double maxFlowUp = 0;
        double minFlowUp = 0;
        double maxFlowDown = 0;
        double minFlowDown = 0;
        for (int i = 0; i < (upFlowArray.length - 1); i++){
            Double a = upFlowArray[i];
            totalUp += a;
            if (maxFlowUp < a){
                maxFlowUp = a;
            }
            if (minFlowUp > a){
                minFlowUp = a;
            }
        }
        for (int i = 0; i < (downFlowArray.length - 1); i++){
            Double a = downFlowArray[i];
            totalDown += a;
            if (maxFlowDown < a){
                maxFlowDown = a;
            }
            if (minFlowDown > a){
                minFlowDown = a;
            }
        }

        //获取平均流畅值和最低流畅值和最高流畅值
        double avrFPS = 0;
        double totalFPS = 0;
        double maxFPS = 0;
        double minFPS = 0;
        for (int i = 0; i < (fpsArray.length - 1); i++){
            Double a = fpsArray[i];
            Double b = fpsArray[i];
            totalFPS += a;
            if(maxFPS < a){
                maxFPS = a;
            }
            if(minFPS > b){
                minFPS = b;
            }
        }
        avrFPS = totalFPS / (fpsArray.length - 1);

        report1.getFlowData().sort((o1, o2) -> {
            Long a = o1.getTime();
            Long b = o2.getTime();
            if (a > b){
                return 1;
            }else if (a < b){
                return -1;
            }else {
                return 0;
            }
        });

        log.info("上下行流量排序后的时间{}", System.currentTimeMillis());

        int j = 0;
        for (int i = 0; i < report1.getCommonData().size(); i++){
            for (; j < report1.getFlowData().size(); j++){
                Long a = report1.getCommonData().get(i).getTime();
                Long b = report1.getFlowData().get(j).getTime();
                Double c = report1.getFlowData().get(j).getUpFlow();
                Double d = report1.getFlowData().get(j).getDownFlow();
                Double e = c + d;
                if (b > a){
                    report1.getCommonData().get(i).setOnePointFlowData(e);
                    break;
                }else {
                    report1.getCommonData().get(i).setOnePointFlowData(0.0);
                }

            }
        }

        log.info("上下行流量计算后的时间{}", System.currentTimeMillis());

        report1.setAvrCPU(avrCPU);
        report1.setAvrFPS(avrFPS);
        report1.setAvrMemory(avrMemory);
        report1.setMaxCPU(maxCPU);
        report1.setMaxMemory(maxMemory);
        report1.setMaxFPS(maxFPS);
        report1.setTotalDown(totalDown);
        report1.setTotalUp(totalUp);
        report1.setMaxFlowUp(maxFlowUp);
        report1.setMinFlowUp(minFlowUp);
        report1.setMaxFlowDown(maxFlowDown);
        report1.setMinFlowDown(minFlowDown);
        report1.setMinCPU(minCPU);
        report1.setMinFPS(minFPS);
        report1.setMinMemory(minMemory);
        report1.setHavrCPU(0);
        report1.setHavrFPS(0);
        report1.setHavrMemory(0);
        report1.setHmaxCPU(0);
        report1.setHmaxMemory(0);
        report1.setHminFPS(0);
        report1.setHtotalDown(0);
        report1.setHtotalUp(0);

        log.info("即将返回结果值的时间{}", System.currentTimeMillis());
        return report1;
    }

    /**
     * 根据传入的code，获取对应的场景，目前支持6个场景
     * @param code
     * @return
     */
    private String getStatementByCode(String code){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("0", "购买爱盈宝,月进宝");
        hashMap.put("1", "切换tab");
        hashMap.put("2", "浏览资金流水");
        hashMap.put("3", "浏览我的资产、优惠券、加息券");
        hashMap.put("4", "浏览出借记录");
        if (!hashMap.containsKey(code)){
            log.info("code为{},无对应的场景");
            return null;
        }
        String statement = hashMap.get(code);
        log.info("获取code值为{},对应的场景为{}", code, statement);
        return statement;
    }
}
