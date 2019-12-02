package com.iqianjin.test.teststage.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.businessCase.BusinessListDTO;
import com.iqianjin.test.teststage.dto.businessCase.UploadXmindDTO;
import com.iqianjin.test.teststage.entity.BusinessCase;
import com.iqianjin.test.teststage.manager.BusinessCaseManager;
import com.iqianjin.test.teststage.service.BusinessCaseService;
import com.iqianjin.test.teststage.utils.InServerUtil;
import com.iqianjin.test.teststage.vo.BusinessListVO;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BusinessCaseServiceImpl implements BusinessCaseService {

    @Autowired
    BusinessCaseManager businessCaseManager;

    @Value("${businessCaseXmind.dir}")
    private String UPLOAD_DIR;

    private static final long MAX_FILE_SIZE = 100*1024*1024;

    @Override
    public Result saveBusinessCase(MultipartFile file, UploadXmindDTO uploadXmindDTO) {
        BusinessCase businessCase = new BusinessCase();
        log.info("开始上传xmind文件或者zip文件");
        if (!file.isEmpty()){
            String originFileName = file.getOriginalFilename();
            String fileNameNoEx = originFileName.substring(0, originFileName.lastIndexOf("."));
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            long size = file.getSize();

            //新增zip文件，因为Safari会自动吧过大的xmind文件自动转换为zip文件，所以得支持zip文件
            if (!extension.contains("xmind")){
                if (!extension.contains("zip")){
                    return Result.failMsg("上传文件类型错误,仅支持xmind和zip文件");
                }
            }
            if (size > MAX_FILE_SIZE){
                return Result.failMsg("上传文件过大，最大不超过100M");
            }
            if (size == 0){
                return Result.failMsg("上传文件不能为空");
            }
            //判断是否已存在文件名相同的xmind文件或者zip文件
            if (businessCaseManager.findAllFileName().contains(originFileName)){
                return Result.failMsg("已有相同文件名的文件，请重新命名");
            }

            //保存文件到指定目录
            if (!InServerUtil.saveFileToServer(file, UPLOAD_DIR)){
                return Result.failMsg("保存文件到服务器失败，请重试");
            }

            //属性copy到businessCase
            BeanUtils.copyProperties(uploadXmindDTO, businessCase);
            businessCase.setCreateDate(new Date());
            businessCase.setUpdateDate(businessCase.getCreateDate());
            businessCase.setFileName(originFileName);
            businessCase.setFileSize(size);

            try {
                log.info("开始保存businessCase对象{}", businessCase);
                businessCaseManager.saveBusinessCase(businessCase);
                log.info("保存上传的businessCase对象成功");
            }catch (Exception e){
                e.printStackTrace();
                log.info("保存上传的businessCase对象失败");
                return Result.failMsg("上传失败！");
            }
        }
        return Result.success("上传成功！");
    }

    @Override
    public Result<BusinessListVO> businessList(BusinessListDTO businessListDTO) {
        PageHelper.startPage(businessListDTO.getPageNum(), businessListDTO.getPageSize());
        log.info("开始查询功能用例list，无参数查询，默认获取全部");
        BusinessListVO businessListVO = new BusinessListVO();
        try {
            List<BusinessCase> list = businessCaseManager.findAllBusinessCase();
            PageInfo<BusinessCase> list1 = new PageInfo<>(list);
            businessListVO.setList(list1);
            log.info("获取功能用例list成功");
        } catch (Exception e) {
            log.info("获取功能用例list失败");
            e.printStackTrace();
            return Result.failMsg("获取列表失败");
        }
        return Result.success(businessListVO);
    }

    @Override
    public Result<BusinessListVO> businessListByDescription(BusinessListDTO businessListDTO) {
        PageHelper.startPage(businessListDTO.getPageNum(), businessListDTO.getPageSize());
        log.info("开始查询功能用例list，有参数{}进行模糊查询", businessListDTO.getDescription());
        BusinessListVO businessListVO = new BusinessListVO();
        try {
            List<BusinessCase> list = businessCaseManager.findAllBusinessCaseByDescription(businessListDTO.getDescription());
            PageInfo<BusinessCase> list1 = new PageInfo<>(list);
            businessListVO.setList(list1);
            log.info("获取功能用例list成功");
        } catch (Exception e) {
            log.info("获取功能用例list失败");
            e.printStackTrace();
            return Result.failMsg("获取列表失败");
        }
        return Result.success(businessListVO);
    }

    @Override
    public Result updateBusinessCase(BusinessCase businessCase) {
        try {
            log.info("开始更新id为{},描述为{}的功能测试用例对象", businessCase.getId(), businessCase.getDescription());
            businessCaseManager.updateBusinessCase(businessCase);
        } catch (Exception e) {
            log.info("更新id为{},描述为{}的功能测试用例对象失败", businessCase.getId(), businessCase.getDescription());
            e.printStackTrace();
            return Result.failMsg("更新失败");
        }
        return Result.success("更新成功");
    }

    @Override
    public Result deleteBusinessCase(Integer id) {
        try {
            BusinessCase businessCase = businessCaseManager.findBusinessCaseById(id);
            log.info("先删除id为{}，文件名为{}的功能测试用例对象，", id, businessCase.getFileName());
            if (!InServerUtil.deleteFileInServerByFilePath(UPLOAD_DIR + businessCase.getFileName())) {
                return Result.failMsg("删除失败或服务器上文件不存在");
            }
            log.info("再删除表中id为{}的功能测试用例对象", id);
            businessCaseManager.deleteBusinessCaseById(id);
        } catch (Exception e) {
            log.info("删除失败");
            e.printStackTrace();
            return Result.failMsg("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public void downloadBusinessCase(Integer id, HttpServletResponse response) throws IOException {
        String fileName = businessCaseManager.findBusinessCaseById(id).getFileName();
        String filePath = UPLOAD_DIR + fileName;
        log.info("下载文件路径为{}", filePath);
        File file = new File(UPLOAD_DIR, fileName);
        if (!StringUtils.isNullOrEmpty(fileName)) {
            // 设置以流的形式下载文件，这样可以实现任意格式的文件下载
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", " attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.setContentLength((int) file.length());
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[128];
                int count = 0;
                while ((count = fis.read(buffer)) > 0) {
                    response.getOutputStream().write(buffer, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.getOutputStream().flush();
                response.getOutputStream().close();
                fis.close();
            }
        }
    }
}
