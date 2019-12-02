package com.iqianjin.test.teststage.service;


import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.AppPackageListDTO;
import com.iqianjin.test.teststage.entity.AppPackage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AppPackageService {
    List<AppPackage> findAll();
    List<AppPackage> findByParams(AppPackageListDTO appPackageListDTO);
    AppPackage findById(Integer id);
    int insert(AppPackage appPackage);
    int delete(Integer id);

    Result updateFlagDelete(Integer id);
    Result uploadAppPackage(MultipartFile file, AppPackage appPackage);
    void downloadAppPackage(Integer id, HttpServletResponse response);

    AppPackage updatePackage(AppPackage aPackage);

}
