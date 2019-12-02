package com.iqianjin.test.teststage.dto;

import lombok.Data;

@Data
public class TestUserDTO {
    private String nickName;
    private String realName; //暂未使用
    private String mysqlServerName;
    private String mysqlUserName;
    private String mysqlPassWord;//暂未使用，与userName取值一致
    private Integer userNum;
    private Integer coverFlag;//有重名昵称用户
}