package com.iqianjin.test.teststage.service.Imp;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dao.AppPackageMapper;
import com.iqianjin.test.teststage.dto.AppPackageListDTO;
import com.iqianjin.test.teststage.entity.AppPackage;
import com.iqianjin.test.teststage.manager.AppPackageManager;
import com.iqianjin.test.teststage.service.AppPackageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AppPackageServiceImpl implements AppPackageService {

    @Autowired
    private AppPackageMapper packageMapper;
    @Autowired
    private AppPackageManager appPackageManager;
    @Value("${apackage.dir}")
    private String  aPackage_DIR;

    private Logger logger = LoggerFactory.getLogger((this.getClass()));
    private static final long MAX_FILE_SIZE = 100*1024*1024;

    @Override
    public List<AppPackage> findAll() {
        return appPackageManager.findAll();
    }

    @Override
    public List<AppPackage> findByParams(AppPackageListDTO appPackageListDTO) {
        return appPackageManager.findByParams(appPackageListDTO);
    }

    @Override
    public int insert(AppPackage appPackage) {
        return appPackageManager.insert(appPackage);
    }

    @Override
    public AppPackage findById(Integer id) {
        return appPackageManager.findById(id);

    }

    @Override
    public int delete(Integer id) {
        return appPackageManager.deleteById(id);
    }

    @Override
    public Result updateFlagDelete(Integer id){
        try {
            if(appPackageManager.updateFlagDelete(id)){
                return Result.successMsg("删除成功！");
            }
            return Result.successMsg("该数据不存在或者已删除");
        }catch (Exception e){
            log.error("删除出现异常",e);
            return Result.failMsg("删除出现异常");
        }
    }

    @Override
    public AppPackage updatePackage(AppPackage aPackage) {
        Map<String, Object> map = convertObj2Map(aPackage);
        StringBuilder str = new StringBuilder("");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() == null){
                str.append(entry.getKey() + "&");
            }
        }
        String[] ignoreProperties = str.toString().split("&");

        AppPackage originCase = packageMapper.selectById(aPackage.getId());
        BeanUtils.copyProperties(aPackage, originCase, ignoreProperties);
        return originCase;
    }

    @Override
    public Result uploadAppPackage(MultipartFile file, AppPackage appPackage) {
            logger.info("Begin To Upload: ");

            if(!file.isEmpty()){
                String originFileName = file.getOriginalFilename();
                String fileNameNoEx = originFileName.substring(0, originFileName.lastIndexOf("."));
                String extension = originFileName.substring(originFileName.lastIndexOf("."));
                if(!extension.contains("ipa")){
                    if (!extension.contains("apk")) {
                        return Result.failMsg("文件类型错误，目前只支持ipa或者apk");
                    }
                }
                if (appPackage.getType() == 1 && !extension.contains("apk")){
                    return Result.failMsg("Android类型必须上传.apk文件");
                }
                if (appPackage.getType() == 2 && !extension.contains("ipa")){
                    return Result.failMsg("IOS类型必须上传.ipa文件");
                }
                //获取当前时间
                appPackage.setCreateDate(new Date());

                appPackage.setFlag(0);

                long size = file.getSize();
                if(size == 0) {
                    return Result.failMsg("文件不能为空");
                }
                if(size > MAX_FILE_SIZE) {
                    return Result.failMsg("文件过大");
                }
                appPackage.setFileSize(size);
                String uploadTime = String.valueOf(System.currentTimeMillis());
                String newFileName = fileNameNoEx + "-" +uploadTime + extension;
                appPackage.setFileName(newFileName);
                File localFile = new File(aPackage_DIR,newFileName);

                try {
                    localFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(localFile);
                    fos.write(file.getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failMsg("保存上传文件失败，请重试！");
                }
            }
            //保存到数据库
            try {
                appPackageManager.insert(appPackage);
            }catch (Exception e){
                return Result.failMsg("上传失败，请重试！");
            }
            return Result.successMsg("安装包上传成功！");

    }

    @Override
    public void downloadAppPackage(Integer id, HttpServletResponse response) {
        AppPackage appPackage = packageMapper.selectById(id);
        String fileName = appPackage.getFileName();
        File file = new File(aPackage_DIR, fileName);
        response.setContentType("application/octet-stream");
        //名字
        try {
            fileName= URLEncoder.encode(fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 设置以流的形式下载文件，这样可以实现任意格式的文件下载
        response.addHeader("Content-Disposition", " attachment;filename=" + fileName);
        response.setContentLengthLong(file.length());
        response.addHeader("Access-Control-Expose-Headers","Content-Disposition");

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[128];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, count);
            }
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将Objective对象转成Map对象
     * @param obj
     * @return
     */
    private Map convertObj2Map(Object obj){
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if(!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("convertObj2Map Error: " + e);
            return null;
        }

        return map;
    }

}
