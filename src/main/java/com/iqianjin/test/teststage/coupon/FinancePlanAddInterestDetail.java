package com.iqianjin.test.teststage.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "投资项目加息券")
public class FinancePlanAddInterestDetail extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = 5659406698957500551L;

    @ApiModelProperty(value = "用于后台显示的id")
	private String idd;
	
	@ApiModelProperty(value = "批次号")
    private Long interestId;

    @ApiModelProperty(value = "投资的理财计划ID")
    private Long planId;

    @ApiModelProperty(value = "投资项目期号")
    private String planIssue;

    @ApiModelProperty(value = "加息券名称")
    private String interestName;

    @ApiModelProperty(value = "加息额度")
    private Double interestLimit = 0d;

    @ApiModelProperty(value = "加息天数")
	private Integer interestContinueDay;

	@ApiModelProperty(value = "加息额度")
    private String interestLimitName;
    
	@ApiModelProperty(value = "用户ID")
    private Long userId;
    
	@ApiModelProperty(value = "用户手机号")
    private String userMobile;

	@ApiModelProperty(value = "满额可用")
  	private Double quota=0d;
  	
	@ApiModelProperty(value = "加息券类型:1普通;2满减")
  	private Integer interestCondition;
  	
	@ApiModelProperty(value = "发放日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date grantTime = new Date();
    
	@ApiModelProperty(value = "使用日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    
	@ApiModelProperty(value = "失效日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date invalidTime;

	@ApiModelProperty(value = "适用产品")
    private Integer interestType;

	@ApiModelProperty(value = "加息券特性:1普通;2可叠加")
	private Integer interestFeature;

	@ApiModelProperty(value = "状态:1未发放;2未使用;3已使用")
    private Integer status;
    
	@ApiModelProperty(value = "备注")
    private String memo;
    
    //加息券显示状态
	@ApiModelProperty(value = "状态:1未发放;2未使用;3已使用")
    private String statusName;
    
	@ApiModelProperty(value = "加息券使用范围")
    private String interestTypeName;

	@ApiModelProperty(value = "投资项目状态")
    private Integer planStatus;

	/**
	 * todo @liubin 是哪个枚举
	 */
	@ApiModelProperty(value = "投资项目的类型:1整存宝;2整存宝+;3爱月投")
    private Integer planType;

	@ApiModelProperty(value = "锁定期")
    private Integer period;

	/**
	 * todo @liubin 是哪个枚举
	 */
	@ApiModelProperty(value = "零存宝加息券类型:1零存宝;2 零存宝+")
	private Integer demandType;

	/**
	 * todo @liubin 是哪个枚举
	 */
	@ApiModelProperty(value = "加息券特性:1普通;2可叠加")
	private String interestFeatureName;

	@ApiModelProperty(value = "加息期（开始日期）")
	@JSONField(format = "yyyy-MM-dd")
	private Date interestStartDate;

	@ApiModelProperty(value = "加息期（结束日期）")
	@JSONField(format = "yyyy-MM-dd")
	private Date interestEndDate;

	@ApiModelProperty(value = "如果加息券是预约时使用，预约时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	@ApiModelProperty(value = "加息券限额")
	private Double ceiling=0.0D;

	@ApiModelProperty(value = "加息产品天数")
	private Integer interestProductDays;

	@ApiModelProperty(value = "指定期数")
	private Integer planPeriod;

	@ApiModelProperty(value = "产品类型")
	private Integer productScope;

	@ApiModelProperty(value = "指定范围的规则")
	private String ruleMemo;

	/**
	 * todo @liubin 是哪个枚举
	 */
	@ApiModelProperty(value = "奖券分类:1红包;2加息券")
	private Integer couponCategory;

	/**
	 * todo @liubin 是哪个枚举
	 */
	@ApiModelProperty(value = "规则适用场景:1天和月通用;2专用")
	private Integer ruleScope;

	/**
	 * 奖券规使用范围类型（1：等于固定值；2：大于等于固定值；3：小于等于固定值；4：介于并包含固定范围之间；5：包含指定期数）
	 * todo @liubin 是哪个枚举
	 */
	@ApiModelProperty(value = "奖券规使用范围类型")
	private Integer ruleRangeType;

	@ApiModelProperty(value = "奖券规则使用对应值")
	private String ruleRangeValue;

	/**
	 * 通过优惠券服务使用时订单号
	 */
	@ApiModelProperty(value = "通过优惠券服务使用时订单号")
	private String srcDocKey;


	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getInterestTypeName() {
		return interestTypeName;
	}

	public void setInterestTypeName(String interestTypeName) {
		this.interestTypeName = interestTypeName;
	}

	public Long getInterestId() {
		return interestId;
	}

	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}

	public Double getInterestLimit() {
		return interestLimit;
	}

	public void setInterestLimit(Double interestLimit) {
		this.interestLimit = interestLimit;
	}
	public Integer getInterestContinueDay() {
		return interestContinueDay;
	}

	public void setInterestContinueDay(Integer interestContinueDay) {
		this.interestContinueDay = interestContinueDay;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Date getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Integer getInterestType() {
		return interestType;
	}

	public void setInterestType(Integer interestType) {
		this.interestType = interestType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPlanIssue() {
		return planIssue;
	}

	public void setPlanIssue(String planIssue) {
		this.planIssue = planIssue;
	}

	public Integer getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(Integer planStatus) {
		this.planStatus = planStatus;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public String getInterestLimitName() {
		return interestLimitName;
	}

	public void setInterestLimitName(String interestLimitName) {
		this.interestLimitName = interestLimitName;
	}

	public String getIdd() {
		return idd;
	}

	public void setIdd(String idd) {
		this.idd = idd;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Date getInterestStartDate() {
		return interestStartDate;
	}

	public void setInterestStartDate(Date interestStartDate) {
		this.interestStartDate = interestStartDate;
	}

	public Date getInterestEndDate() {
		return interestEndDate;
	}

	public void setInterestEndDate(Date interestEndDate) {
		this.interestEndDate = interestEndDate;
	}

	public Integer getInterestFeature() {
		return interestFeature;
	}

	public void setInterestFeature(Integer interestFeature) {
		this.interestFeature = interestFeature;
	}

	public String getInterestFeatureName() {
		return interestFeatureName;
	}

	public void setInterestFeatureName(String interestFeatureName) {
		this.interestFeatureName = interestFeatureName;
	}

	public Double getQuota() {
		return quota;
	}

	public void setQuota(Double quota) {
		this.quota = quota;
	}

	public Integer getInterestCondition() {
		return interestCondition;
	}

	public void setInterestCondition(Integer interestCondition) {
		this.interestCondition = interestCondition;
	}
	public Integer getDemandType() {
		return demandType;
	}

	public void setDemandType(Integer demandType) {
		this.demandType = demandType;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Double getCeiling() {
		return ceiling;
	}

	public void setCeiling(Double ceiling) {
		this.ceiling = ceiling;
	}

	public Integer getInterestProductDays() {
		return interestProductDays;
	}

	public void setInterestProductDays(Integer interestProductDays) {
		this.interestProductDays = interestProductDays;
	}

	public Integer getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(Integer planPeriod) {
		this.planPeriod = planPeriod;
	}

    public Integer getProductScope() {
        return productScope;
    }

    public void setProductScope(Integer productScope) {
        this.productScope = productScope;
    }

    public String getRuleMemo() {
        return ruleMemo;
    }

    public void setRuleMemo(String ruleMemo) {
        this.ruleMemo = ruleMemo;
    }

    public Integer getCouponCategory() {
        return couponCategory;
    }

    public void setCouponCategory(Integer couponCategory) {
        this.couponCategory = couponCategory;
    }

    public Integer getRuleScope() {
        return ruleScope;
    }

    public void setRuleScope(Integer ruleScope) {
        this.ruleScope = ruleScope;
    }

    public Integer getRuleRangeType() {
        return ruleRangeType;
    }

    public void setRuleRangeType(Integer ruleRangeType) {
        this.ruleRangeType = ruleRangeType;
    }

    public String getRuleRangeValue() {
        return ruleRangeValue;
    }

    public void setRuleRangeValue(String ruleRangeValue) {
        this.ruleRangeValue = ruleRangeValue;
    }

	public String getSrcDocKey() {
		return srcDocKey;
	}

	public void setSrcDocKey(String srcDocKey) {
		this.srcDocKey = srcDocKey;
	}
}
