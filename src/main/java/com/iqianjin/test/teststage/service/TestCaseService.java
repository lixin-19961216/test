package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.testcase.TestCaseDelReqDTO;
import com.iqianjin.test.teststage.dto.testcase.TestCaseListByTypeReqDTO;
import com.iqianjin.test.teststage.dto.testcase.TestCaseSaveReqDTO;
import com.iqianjin.test.teststage.entity.TestCase;
import com.iqianjin.test.teststage.vo.TestCaseListVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 测试用例相关
 */
public interface TestCaseService {
    /**
     * 根据上传的excel文件，保存用例
     * @param file
     * @return
     * @throws IOException
     */
    Result readExceltToTestCase(MultipartFile file) throws IOException;

    /**
     * 获取testcase列表
     * @param typeReqDTO
     * @return
     */
    Result<TestCaseListVO> testCaseList(TestCaseListByTypeReqDTO typeReqDTO);

    /**
     * 保存接口case
     * @param testCaseDTO
     * @return
     */
    Result saveTestCase(TestCaseSaveReqDTO testCaseDTO);

    /**
     * 根据id list，删除接口case
     * @param id
     */
    Result delTestCaseByIds(Integer id);

    /**
     * 编辑接口case
     * @param testCase
     */
    Result modifyTestCase(TestCase testCase);
}
