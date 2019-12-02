package com.iqianjin.test.teststage.entity;

import java.util.Date;

public class ModuleRelationship {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 平台~包括app,web,h5等
     */
    private String platInfo;

    /**
     * 功能模块
     */
    private String featureModule;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public String getPlatInfo() {
        return platInfo;
    }

    public void setPlatInfo(String platInfo) {
        this.platInfo = platInfo == null ? null : platInfo.trim();
    }

    public String getFeatureModule() {
        return featureModule;
    }

    public void setFeatureModule(String featureModule) {
        this.featureModule = featureModule == null ? null : featureModule.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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