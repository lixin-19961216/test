package com.iqianjin.test.teststage.vo;

import com.iqianjin.test.teststage.dto.performanceReport.CommonData;
import com.iqianjin.test.teststage.dto.performanceReport.FlowData;

import java.io.Serializable;
import java.util.List;

public class PerformanceReportVO implements Serializable{
    public double getAvrCPU() {
        return avrCPU;
    }

    public void setAvrCPU(double avrCPU) {
        this.avrCPU = avrCPU;
    }

    public double getMaxCPU() {
        return maxCPU;
    }

    public void setMaxCPU(double maxCPU) {
        this.maxCPU = maxCPU;
    }

    public double getAvrMemory() {
        return avrMemory;
    }

    public void setAvrMemory(double avrMemory) {
        this.avrMemory = avrMemory;
    }

    public double getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(double maxMemory) {
        this.maxMemory = maxMemory;
    }

    public double getTotalUp() {
        return totalUp;
    }

    public void setTotalUp(double totalUp) {
        this.totalUp = totalUp;
    }

    public double getTotalDown() {
        return totalDown;
    }

    public void setTotalDown(double totalDown) {
        this.totalDown = totalDown;
    }

    public double getAvrFPS() {
        return avrFPS;
    }

    public void setAvrFPS(double avrFPS) {
        this.avrFPS = avrFPS;
    }

    public double getMinFPS() {
        return minFPS;
    }

    public void setMinFPS(double minFPS) {
        this.minFPS = minFPS;
    }


    public double getHavrCPU() {
        return havrCPU;
    }

    public void setHavrCPU(double havrCPU) {
        this.havrCPU = havrCPU;
    }

    public double getHmaxCPU() {
        return hmaxCPU;
    }

    public void setHmaxCPU(double hmaxCPU) {
        this.hmaxCPU = hmaxCPU;
    }

    public double getHavrMemory() {
        return havrMemory;
    }

    public void setHavrMemory(double havrMemory) {
        this.havrMemory = havrMemory;
    }

    public double getHmaxMemory() {
        return hmaxMemory;
    }

    public void setHmaxMemory(double hmaxMemory) {
        this.hmaxMemory = hmaxMemory;
    }

    public double getHtotalUp() {
        return htotalUp;
    }

    public void setHtotalUp(double htotalUp) {
        this.htotalUp = htotalUp;
    }

    public double getHtotalDown() {
        return htotalDown;
    }

    public void setHtotalDown(double htotalDown) {
        this.htotalDown = htotalDown;
    }

    public double getHavrFPS() {
        return havrFPS;
    }

    public void setHavrFPS(double havrFPS) {
        this.havrFPS = havrFPS;
    }

    public double getHminFPS() {
        return hminFPS;
    }

    public void setHminFPS(double hminFPS) {
        this.hminFPS = hminFPS;
    }

    public void PerformanceReport(String statement, String startTime, List<CommonData> commonData,
                                  List<FlowData> flowData, double avrCPU, double maxCPU, double minCPU,double avrMemory,
                                  double maxMemory, double minMemory, double totalUp, double totalDown, double maxFlowUp,
                                  double minFlowUp, double maxFlowDown, double minFlowDown, double avrFPS,
                                  double maxFPS, double minFPS, double havrCPU, double hmaxCPU, double havrMemory,
                                  double hmaxMemory, double htotalUp, double htotalDown, double havrFPS,
                                  double hminFPS, String maxUpFlowUrl, String maxDownFlowUrl) {
        this.statement = statement;
        this.startTime = startTime;
        this.commonData = commonData;
        this.flowData = flowData;
        this.avrCPU = avrCPU;
        this.maxCPU = maxCPU;
        this.minCPU = minCPU;
        this.avrMemory = avrMemory;
        this.maxMemory = maxMemory;
        this.minMemory = minMemory;
        this.totalUp = totalUp;
        this.totalDown = totalDown;
        this.maxFlowUp = maxFlowUp;
        this.minFlowUp = minFlowUp;
        this.maxFlowDown = maxFlowDown;
        this.minFlowDown = minFlowDown;
        this.avrFPS = avrFPS;
        this.maxFPS = maxFPS;
        this.minFPS = minFPS;
        this.havrCPU = havrCPU;
        this.hmaxCPU = hmaxCPU;
        this.havrMemory = havrMemory;
        this.hmaxMemory = hmaxMemory;
        this.htotalUp = htotalUp;
        this.htotalDown = htotalDown;
        this.havrFPS = havrFPS;
        this.hminFPS = hminFPS;
        this.maxUpFlowUrl = maxUpFlowUrl;
        this.maxDownFlowUrl = maxDownFlowUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    private String statement;

    private String startTime;

    private List<CommonData> commonData;

    private List<FlowData> flowData;

    public List<CommonData> getCommonData() {
        return commonData;
    }

    public void setCommonData(List<CommonData> commonData) {
        this.commonData = commonData;
    }

    public List<FlowData> getFlowData() {
        return flowData;
    }

    public void setFlowData(List<FlowData> flowData) {
        this.flowData = flowData;
    }

    private double phoneMemory;

    private String phoneName;

    private String phoneSystem;

    private String versionCode;

    private double testTime;

    public double getPhoneMemory() {
        return phoneMemory;
    }

    public void setPhoneMemory(double phoneMemory) {
        this.phoneMemory = phoneMemory;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneSystem() {
        return phoneSystem;
    }

    public void setPhoneSystem(String phoneSystem) {
        this.phoneSystem = phoneSystem;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public double getTestTime() {
        return testTime;
    }

    public void setTestTime(double testTime) {
        this.testTime = testTime;
    }

    public double getMinCPU() {
        return minCPU;
    }

    public void setMinCPU(double minCPU) {
        this.minCPU = minCPU;
    }

    public double getMinMemory() {
        return minMemory;
    }

    public void setMinMemory(double minMemory) {
        this.minMemory = minMemory;
    }

    public double getMaxFPS() {
        return maxFPS;
    }

    public void setMaxFPS(double maxFPS) {
        this.maxFPS = maxFPS;
    }


    public double getMaxFlowUp() {
        return maxFlowUp;
    }

    public void setMaxFlowUp(double maxFlowUp) {
        this.maxFlowUp = maxFlowUp;
    }

    public double getMinFlowUp() {
        return minFlowUp;
    }

    public void setMinFlowUp(double minFlowUp) {
        this.minFlowUp = minFlowUp;
    }

    public double getMaxFlowDown() {
        return maxFlowDown;
    }

    public void setMaxFlowDown(double maxFlowDown) {
        this.maxFlowDown = maxFlowDown;
    }

    public double getMinFlowDown() {
        return minFlowDown;
    }

    public void setMinFlowDown(double minFlowDown) {
        this.minFlowDown = minFlowDown;
    }

    public String getMaxUpFlowUrl() {
        return maxUpFlowUrl;
    }

    public void setMaxUpFlowUrl(String maxUpFlowUrl) {
        this.maxUpFlowUrl = maxUpFlowUrl;
    }

    public String getMaxDownFlowUrl() {
        return maxDownFlowUrl;
    }

    public void setMaxDownFlowUrl(String maxDownFlowUrl) {
        this.maxDownFlowUrl = maxDownFlowUrl;
    }

    private String maxUpFlowUrl;

    private String maxDownFlowUrl;

    private double avrCPU;

    private double maxCPU;

    private double minCPU;

    private double avrMemory;

    private double maxMemory;

    private double minMemory;

    private double totalUp;

    private double totalDown;

    private double maxFlowUp;

    private double minFlowUp;

    private double maxFlowDown;

    private double minFlowDown;

    private double avrFPS;

    private double maxFPS;

    private double minFPS;

    private double havrCPU;

    private double hmaxCPU;

    private double havrMemory;

    private double hmaxMemory;

    private double htotalUp;

    private double htotalDown;

    private double havrFPS;

    private double hminFPS;
}
