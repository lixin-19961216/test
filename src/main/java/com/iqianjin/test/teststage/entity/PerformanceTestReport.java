package com.iqianjin.test.teststage.entity;

import java.util.Date;

public class PerformanceTestReport {
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
    private Integer platForm;

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
     * 备注
     */
    private String remark;

    /**
     * 标志位，0代表未删除；1代表已删除，前端不展示；
     */
    private Integer flag;

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

    public Integer getPlatForm() {
        return platForm;
    }

    public void setPlatForm(Integer platForm) {
        this.platForm = platForm;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }


}