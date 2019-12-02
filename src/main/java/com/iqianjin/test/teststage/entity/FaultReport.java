package com.iqianjin.test.teststage.entity;

import java.util.Date;

public class FaultReport {
    /**
     * id自增
     */
    private Integer id;

    /**
     * 故障简述
     */
    private String faultDescribe;

    /**
     * 故障等级
     */
    private String faultLevel;

    /**
     * 故障发现时间
     */
    private Date startTime;

    /**
     * 故障解决时间
     */
    private Date endTime;

    /**
     * 发现来源
     */
    private String discoverySource;

    /**
     * 影响可用率
     */
    private String influenceAvailability;

    /**
     * 资金损失
     */
    private String capitalLoss;

    /**
     * 故障类别
     */
    private String faultCategory;

    /**
     * 责任人
     */
    private String personLiable;

    /**
     * 责任团队
     */
    private String responsibilityTeam;

    /**
     * 故障处理过程
     */
    private String processingProcedure;

    /**
     * 影响产品线
     */
    private String impactProductline;

    /**
     * 影响核心指标
     */
    private String impactCoreindicators;

    /**
     * 影响客服
     */
    private String impactCustomservice;

    /**
     * 影响功能
     */
    private String impactFunction;

    /**
     * 故障原因
     */
    private String faultReason;

    /**
     * 故障第一发现人
     */
    private String faultFirstdiscovery;

    /**
     * 故障第一处理人
     */
    private String faultFirsthandlingperson;

    /**
     * SLA执行情况
     */
    private String faultSla;

    /**
     * 后续ACTION
     */
    private String followupAction;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe == null ? null : faultDescribe.trim();
    }

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel == null ? null : faultLevel.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDiscoverySource() {
        return discoverySource;
    }

    public void setDiscoverySource(String discoverySource) {
        this.discoverySource = discoverySource == null ? null : discoverySource.trim();
    }

    public String getInfluenceAvailability() {
        return influenceAvailability;
    }

    public void setInfluenceAvailability(String influenceAvailability) {
        this.influenceAvailability = influenceAvailability == null ? null : influenceAvailability.trim();
    }

    public String getCapitalLoss() {
        return capitalLoss;
    }

    public void setCapitalLoss(String capitalLoss) {
        this.capitalLoss = capitalLoss == null ? null : capitalLoss.trim();
    }

    public String getFaultCategory() {
        return faultCategory;
    }

    public void setFaultCategory(String faultCategory) {
        this.faultCategory = faultCategory == null ? null : faultCategory.trim();
    }

    public String getPersonLiable() {
        return personLiable;
    }

    public void setPersonLiable(String personLiable) {
        this.personLiable = personLiable == null ? null : personLiable.trim();
    }

    public String getResponsibilityTeam() {
        return responsibilityTeam;
    }

    public void setResponsibilityTeam(String responsibilityTeam) {
        this.responsibilityTeam = responsibilityTeam == null ? null : responsibilityTeam.trim();
    }

    public String getProcessingProcedure() {
        return processingProcedure;
    }

    public void setProcessingProcedure(String processingProcedure) {
        this.processingProcedure = processingProcedure == null ? null : processingProcedure.trim();
    }

    public String getImpactProductline() {
        return impactProductline;
    }

    public void setImpactProductline(String impactProductline) {
        this.impactProductline = impactProductline == null ? null : impactProductline.trim();
    }

    public String getImpactCoreindicators() {
        return impactCoreindicators;
    }

    public void setImpactCoreindicators(String impactCoreindicators) {
        this.impactCoreindicators = impactCoreindicators == null ? null : impactCoreindicators.trim();
    }

    public String getImpactCustomservice() {
        return impactCustomservice;
    }

    public void setImpactCustomservice(String impactCustomservice) {
        this.impactCustomservice = impactCustomservice == null ? null : impactCustomservice.trim();
    }

    public String getImpactFunction() {
        return impactFunction;
    }

    public void setImpactFunction(String impactFunction) {
        this.impactFunction = impactFunction == null ? null : impactFunction.trim();
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason == null ? null : faultReason.trim();
    }

    public String getFaultFirstdiscovery() {
        return faultFirstdiscovery;
    }

    public void setFaultFirstdiscovery(String faultFirstdiscovery) {
        this.faultFirstdiscovery = faultFirstdiscovery == null ? null : faultFirstdiscovery.trim();
    }

    public String getFaultFirsthandlingperson() {
        return faultFirsthandlingperson;
    }

    public void setFaultFirsthandlingperson(String faultFirsthandlingperson) {
        this.faultFirsthandlingperson = faultFirsthandlingperson == null ? null : faultFirsthandlingperson.trim();
    }

    public String getFaultSla() {
        return faultSla;
    }

    public void setFaultSla(String faultSla) {
        this.faultSla = faultSla == null ? null : faultSla.trim();
    }

    public String getFollowupAction() {
        return followupAction;
    }

    public void setFollowupAction(String followupAction) {
        this.followupAction = followupAction == null ? null : followupAction.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}