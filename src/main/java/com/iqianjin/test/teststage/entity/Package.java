package com.iqianjin.test.teststage.entity;

import java.util.Date;

public class Package {
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
    private Byte type;

    /**
     * 1:debug包（可切换host）, 2:V3包（不可切换host）, 3:生产包（不可切换host）
     */
    private Byte packageType;

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
    private Date releaseDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 标志位，0代表未删除；1代表已删除，前端不展示；
     */
    private Byte flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getPackageType() {
        return packageType;
    }

    public void setPackageType(Byte packageType) {
        this.packageType = packageType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser == null ? null : uploadUser.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri == null ? null : fileUri.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }
}