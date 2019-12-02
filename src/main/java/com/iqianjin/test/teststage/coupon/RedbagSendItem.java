package com.iqianjin.test.teststage.coupon;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 红包明细
 */
public class RedbagSendItem implements Serializable {

    private static final long serialVersionUID = 284104584707331651L;

    /**
     * 红包发放明细主键id
     */
    private Long redbagItemId;

    /**
     * 红包主键id
     */
    private Integer redbagId;

    /**
     * 红包名称
     */
    private String redbagName;

    /**
     * 红包面额
     */
    private Double redbagPar = 0d;

    /**
     * 红包适用范围（1：通用；2：整存宝；3：零钱通）
     */
    private int redbagScope;

    /**
     * 备注
     */
    private String remark;

    /**
     * 红包发放后的使用状态(1：未发放，2：未使用，3：已使用，4：已过期)
     */
    private int redbagItemStatus;

    /**
     * 红包所属者id
     */
    private Long redbagOwnerId;

    /**
     * 红包使用时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date redbagUseDate;

    /**
     * 红包发放时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date redbagSendDatetime;

    /**
     * 红包所属者手机号
     */
    private String redbagOwnerPhone;

    /**
     * 红包所属者昵称
     */
    private String redbagOwnerNickname;

    /**
     * 红包有效期截止时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date redbagValidityEnddate;

    /**
     * 红包发放明细创建人
     */
    private String redbagItemCreateUser;

    /**
     * 红包发放明细创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date redbagItemCreateDatetime;

    /**
     * 用户资金明细表ID
     */
    private Long userPointDetailId;

    /**
     * 1:购买投资项目；2:购买零钱通；3:购买债权
     */
    private int buyType;

    /**
     * 零钱通ID、整存宝ID、债权ID
     */
    private Long businessId;

    private int version;

    /**
     * 红包类型
     */
    private Integer redbagType;
    /**
     * 满额可用
     */
    private Double quota;

    private Integer importUserId;

    private Integer sendUserId;

    /**
     * 整存宝+类型的红包的期数 1：月，2: 6月，3：12月，4：通用
     */
    private Integer redbagPeriod;

    /**
     * 用户微信ID
     */
    private String openId;

    /**
     * 通过优惠券服务使用时订单号
     */
    private String srcDocKey;

    /**
     * 初始金额，不为空说明红包被动态叠加过，展示时要加以区分
     */
    private Double initPar;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getRedbagPeriod() {
        return redbagPeriod;
    }

    public void setRedbagPeriod(Integer redbagPeriod) {
        this.redbagPeriod = redbagPeriod;
    }

    public Integer getImportUserId() {
        return importUserId;
    }

    public void setImportUserId(Integer importUserId) {
        this.importUserId = importUserId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getRedbagType() {
        return redbagType;
    }

    public void setRedbagType(Integer redbagType) {
        this.redbagType = redbagType;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public Long getRedbagItemId() {
        return redbagItemId;
    }

    public void setRedbagItemId(Long redbagItemId) {
        this.redbagItemId = redbagItemId;
    }

    public Integer getRedbagId() {
        return redbagId;
    }

    public void setRedbagId(Integer redbagId) {
        this.redbagId = redbagId;
    }

    public String getRedbagName() {
        return redbagName;
    }

    public void setRedbagName(String redbagName) {
        this.redbagName = redbagName;
    }

    public Double getRedbagPar() {
        return redbagPar;
    }

    public void setRedbagPar(Double redbagPar) {
        this.redbagPar = redbagPar;
    }

    public int getRedbagScope() {
        return redbagScope;
    }

    public void setRedbagScope(int redbagScope) {
        this.redbagScope = redbagScope;
    }

    public int getRedbagItemStatus() {
        return redbagItemStatus;
    }

    public void setRedbagItemStatus(int redbagItemStatus) {
        this.redbagItemStatus = redbagItemStatus;
    }

    public Long getRedbagOwnerId() {
        return redbagOwnerId;
    }

    public void setRedbagOwnerId(Long redbagOwnerId) {
        this.redbagOwnerId = redbagOwnerId;
    }

    public Date getRedbagUseDate() {
        return redbagUseDate;
    }

    public void setRedbagUseDate(Date redbagUseDate) {
        this.redbagUseDate = redbagUseDate;
    }

    public Date getRedbagSendDatetime() {
        return redbagSendDatetime;
    }

    public void setRedbagSendDatetime(Date redbagSendDatetime) {
        this.redbagSendDatetime = redbagSendDatetime;
    }

    public String getRedbagOwnerPhone() {
        return redbagOwnerPhone;
    }

    public void setRedbagOwnerPhone(String redbagOwnerPhone) {
        this.redbagOwnerPhone = redbagOwnerPhone;
    }

    public String getRedbagOwnerNickname() {
        return redbagOwnerNickname;
    }

    public void setRedbagOwnerNickname(String redbagOwnerNickname) {
        this.redbagOwnerNickname = redbagOwnerNickname;
    }

    public Date getRedbagValidityEnddate() {
        return redbagValidityEnddate;
    }

    public void setRedbagValidityEnddate(Date redbagValidityEnddate) {
        this.redbagValidityEnddate = redbagValidityEnddate;
    }

    public String getRedbagItemCreateUser() {
        return redbagItemCreateUser;
    }

    public void setRedbagItemCreateUser(String redbagItemCreateUser) {
        this.redbagItemCreateUser = redbagItemCreateUser;
    }

    public Date getRedbagItemCreateDatetime() {
        return redbagItemCreateDatetime;
    }

    public void setRedbagItemCreateDatetime(Date redbagItemCreateDatetime) {
        this.redbagItemCreateDatetime = redbagItemCreateDatetime;
    }

    public Long getUserPointDetailId() {
        return userPointDetailId;
    }

    public void setUserPointDetailId(Long userPointDetailId) {
        this.userPointDetailId = userPointDetailId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getSrcDocKey() {
        return srcDocKey;
    }

    public void setSrcDocKey(String srcDocKey) {
        this.srcDocKey = srcDocKey;
    }

    public Double getInitPar() {
        return initPar;
    }

    public void setInitPar(Double initPar) {
        this.initPar = initPar;
    }
}
