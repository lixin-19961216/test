package com.iqianjin.test.teststage.dao;

import com.iqianjin.test.teststage.entity.ServerInfo;

import java.util.List;

public interface ServerInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerInfo record);

    int insertSelective(ServerInfo record);

    ServerInfo selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(ServerInfo record);

    int updateByPrimaryKey(ServerInfo record);

    List<ServerInfo> findAllServerInfo();

    List<String> getAllServerInfo();

    ServerInfo findServerInfoByApiUrl(String apiUrl);
}