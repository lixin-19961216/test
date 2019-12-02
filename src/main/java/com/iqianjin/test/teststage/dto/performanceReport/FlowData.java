package com.iqianjin.test.teststage.dto.performanceReport;

public class FlowData {
    Long duration;
    Double downFlow;
    Double upFlow;
    Long time;
    String url;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Double getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Double downFlow) {
        this.downFlow = downFlow;
    }

    public Double getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Double upFlow) {
        this.upFlow = upFlow;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
