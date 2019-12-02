package com.iqianjin.test.teststage.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.testcase.TestCaseDelReqDTO;
import com.iqianjin.test.teststage.dto.testcase.TestCaseListByTypeReqDTO;
import com.iqianjin.test.teststage.dto.testcase.TestCaseSaveReqDTO;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.manager.TestCaseExcelUploadManager;
import com.iqianjin.test.teststage.manager.TestCaseManager;
import com.iqianjin.test.teststage.service.TestCaseService;
import com.iqianjin.test.teststage.utils.ExcelReadUtil;
import com.iqianjin.test.teststage.vo.TestCaseListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseServiceImpl.class);

    @Autowired
    TestCaseExcelUploadManager testCaseExcelUploadManager;

    @Autowired
    TestCaseManager testCaseManager;
    @Override
    public Result readExceltToTestCase(MultipartFile file) throws IOException {

        List<String[]> list = ExcelReadUtil.readExcel(file);

        List<TestCase> testCaseList = new ArrayList<>();

        if (list.size() == 0) {
            return Result.failMsg("数据数据为空,请重新选择文件");
        }

        if (list.size() > 1000) {
            return Result.failMsg("数据量超过1千条,请重新选择文件");
        }

        for (String[] excel : list){
            TestCase testCase = new TestCase();

            testCase.setPlatInfo(excel[0]);
            testCase.setFeatureModule(excel[1]);
            testCase.setCaseNo(excel[2]);
            testCase.setDescription(excel[3]);
            testCase.setApiInfo(excel[4]);
            testCase.setMethod(excel[5]);
            testCase.setHeaders(excel[6]);
            testCase.setParams(excel[7]);
            testCase.setUserInfo(excel[8]);
            testCase.setMysql(excel[9]);
            testCase.setMongodb(excel[10]);
            testCase.setRedis(excel[11]);
            testCase.setExpectMysql(excel[12]);
            testCase.setExpectResponse(excel[13]);
            testCase.setClearMysql(excel[14]);
            testCase.setClearRedis(excel[15]);
            testCase.setSuccCount(0);
            testCase.setFailCount(0);

            testCaseList.add(testCase);
            }
        LOGGER.info("展示上传excel中第一行转换为TestCase对象:{}",testCaseList.get(0));

        for (TestCase testCase : testCaseList){
            try {
                testCaseExcelUploadManager.saveExcelUploadTestCase(testCase);
            }catch (Exception e){
                LOGGER.info("excel上传的数据入库失败");
                e.printStackTrace();
                return Result.builder()
                        .code(-1)
                        .setMsg("上传Excel入库失败");
            }
        }
//        return Result.success(list.size(),"上传excel入库成功，请在接口用例列表页查看。");
        return Result.builder()
                .code(1)
                .setMsg("上传excel入库成功，共" + String.valueOf(list.size()) + "条用例");
    }

    @Override
    public Result<TestCaseListVO> testCaseList(TestCaseListByTypeReqDTO typeReqDTO) {
        PageHelper.startPage(typeReqDTO.getPageNum(), typeReqDTO.getPageSize());
        TestCaseListVO testCaseListVO = new TestCaseListVO();
        List<TestCase> list = new ArrayList<>();

        //platInfo字段必须存在
        if (StringUtils.isEmpty(typeReqDTO.getPlatInfo())){
            return Result.failMsg("请求参数中,platInfo字段必须存在");
        }

        //判断是否含有featureModule字段
        try {
            if (StringUtils.isEmpty(typeReqDTO.getFeatureModule()) && StringUtils.isEmpty(typeReqDTO.getDescription())){
                LOGGER.info("开始根据platInfo:{}查询用例列表。", typeReqDTO.getPlatInfo());
                list = testCaseManager.findTestCaseListByPlatInfo(typeReqDTO.getPlatInfo());
            }else if (StringUtils.isEmpty(typeReqDTO.getDescription())){
                LOGGER.info("开始根据platInfo:{}和featureModule:{}查询用例列表。", typeReqDTO.getPlatInfo(), typeReqDTO.getFeatureModule());
                list = testCaseManager.findTestCaseListByPlatInfoAndModule(typeReqDTO.getPlatInfo(), typeReqDTO.getFeatureModule());
            }else if (StringUtils.isEmpty(typeReqDTO.getFeatureModule()))
            {
                LOGGER.info("开始根据platInfo:{}和description:{}查询用例列表。", typeReqDTO.getPlatInfo(), typeReqDTO.getDescription());
                list = testCaseManager.findTestCaseListByPlatInfoAndDescription(typeReqDTO.getPlatInfo(), typeReqDTO.getDescription());
            }else {
                LOGGER.info("开始根据platInfo:{}和featureModule:{}和description:{}查询用例列表。", typeReqDTO.getPlatInfo(), typeReqDTO.getFeatureModule(), typeReqDTO.getDescription());
                list = testCaseManager.findTestCaseListByPlatInfoAndModuleAndDescription(typeReqDTO.getPlatInfo(),
                        typeReqDTO.getFeatureModule(), typeReqDTO.getDescription());
            }
            PageInfo<TestCase> pageInfo = new PageInfo<TestCase>(list);
            testCaseListVO.setTestCaseList(pageInfo);
        }catch (Exception e){
            LOGGER.info("查询用例列表失败。");
            e.printStackTrace();
            return Result.failMsg("查询列表失败");
        }
        return Result.success(testCaseListVO).setMsg("查询成功");
    }

    @Override
    public Result saveTestCase(TestCaseSaveReqDTO testCaseDTO) {
        TestCase testCase = new TestCase();

        //利用BeanUtils工具类，复制属性
        BeanUtils.copyProperties(testCaseDTO, testCase);

        try {
            LOGGER.info("开始保存接口case{}", testCase);
            testCaseManager.saveTestCase(testCase);
        }catch (Exception e){
            LOGGER.info("保存用例失败");
            e.printStackTrace();
            return Result.failMsg("保存失败");
        }
        return Result.success("保存成功");
    }

    @Override
    public Result delTestCaseByIds(Integer id) {
        try {
            LOGGER.info("开始删除id为{}的接口用例", id);
            testCaseManager.delTestCaseById(id);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("删除id为{}的用例时，删除失败", id);
            return Result.failMsg("删除失败，请重试。");
        }
        return Result.success("删除成功！");
    }

    @Override
    public Result modifyTestCase(TestCase testCase) {
        LOGGER.info("开始编辑id为{}，描述为{}的测试用例", testCase.getId(), testCase.getDescription());
        try {
            testCaseManager.modifyTestCase(testCase);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failMsg("编辑描述为" + testCase.getDescription() + "的用例失败");
        }
        return Result.success("编辑成功！");
    }
}
