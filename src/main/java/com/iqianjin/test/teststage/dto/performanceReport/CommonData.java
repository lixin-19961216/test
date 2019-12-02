package com.iqianjin.test.teststage.dto.performanceReport;

public class CommonData {
    Double memory;
    Double fps;
    Double cpu;
    Double onePointFlowData;
    Long time;
    String pageName;

    public Double getMemory() {
        return memory;
    }

    public void setMemory(Double memory) {
        this.memory = memory;
    }

    public Double getFps() {
        return fps;
    }

    public void setFps(Double fps) {
        this.fps = fps;
    }

    public Double getCpu() {
        return cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public Double getOnePointFlowData() {
        return onePointFlowData;
    }

    public void setOnePointFlowData(Double onePointFlowData) {
        this.onePointFlowData = onePointFlowData;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
