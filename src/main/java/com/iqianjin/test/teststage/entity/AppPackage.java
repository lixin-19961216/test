package com.iqianjin.test.teststage.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AppPackage {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 版本
     */
    private String version;

    /**
     * 1:Android, 2:IOS
     */
    private Integer type;

    /**
     * 1:debug包（可切换host）, 2:V3包（不可切换host）, 3:生产包（不可切换host）
     */
    private Integer packageType;

    /**
     * 上传时间
     */
    private Date createDate;

    /**
     * 上传人
     */
    private String uploadUser;

    /**
     * 上传文件名称
     */
    private String fileName;

    /**
     * 上传文件uri
     */
    private String fileUri;

    /**
     * 上传文件大小  byte length
     */
    private Long fileSize;

    /**
     * 发包时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 标志位，0代表未删除；1代表已删除，前端不展示；
     */
    private Integer flag;

    }