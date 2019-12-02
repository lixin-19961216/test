package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.businessCase.BusinessListDTO;
import com.iqianjin.test.teststage.dto.businessCase.UploadXmindDTO;
import com.iqianjin.test.teststage.entity.BusinessCase;
import com.iqianjin.test.teststage.vo.BusinessListVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface BusinessCaseService {
    /**
     * 保存功能测试用例对象
     * @param file
     * @param uploadXmindDTO
     */
    Result saveBusinessCase(MultipartFile file, UploadXmindDTO uploadXmindDTO);

    /**
     * 只是获取列表
     * @param businessListDTO
     * @return
     */
    Result<BusinessListVO> businessList(BusinessListDTO businessListDTO);

    /**
     * 根据description模糊查询列表
     * @param businessListDTO
     * @return
     */
    Result<BusinessListVO> businessListByDescription(BusinessListDTO businessListDTO);

    /**
     * 更新功能测试用例对象
     * @param businessCase
     * @return
     */
    Result updateBusinessCase(BusinessCase businessCase);

    /**
     * 根据id删除功能测试用例对象
     * @param id
     * @return
     */
    Result deleteBusinessCase(Integer id);

    /**
     * 根据id下载对应的xml文件
     * @param id
     * @param response
     * @return
     */
    void downloadBusinessCase(Integer id, HttpServletResponse response) throws IOException;
}
