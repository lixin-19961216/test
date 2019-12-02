package com.iqianjin.test.teststage.entity;

public class ServerInfo {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 接口请求ip
     */
    private String apiUrl;

    /**
     * 对应接口请求ip的描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl == null ? null : apiUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}