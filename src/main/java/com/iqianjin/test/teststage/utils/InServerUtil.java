package com.iqianjin.test.teststage.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
/**
 * 再服务器上进行一些操作的方法工具集合
 */
public class InServerUtil {
    public static boolean saveFileToServer(MultipartFile file, String filePath){
        boolean temp = false;
        File file1 = new File(filePath, file.getOriginalFilename());
        try {
            file1.createNewFile();
            FileOutputStream fos = new FileOutputStream(file1);
            fos.write(file.getBytes());
            fos.close();
            temp = true;
        }catch (Exception e){
            log.info("将文件保存到服务器上时失败");
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 根据文件路径，删除对应文件
     * @param filePath
     * @return
     */
    public static boolean deleteFileInServerByFilePath(String filePath){
        boolean resultInfo;
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                resultInfo =  true;
                log.info("删除文件" + filePath + "成功");
            } else {
                resultInfo =  false;
                log.info("删除文件" + filePath + "失败");
            }
        } else {
            resultInfo = false;
            log.info("文件" + filePath + "不存在，删除失败");
        }
        return resultInfo;
    }
}
