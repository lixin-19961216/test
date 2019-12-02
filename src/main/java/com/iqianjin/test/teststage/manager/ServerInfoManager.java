package com.iqianjin.test.teststage.manager;

import com.iqianjin.test.teststage.entity.ServerInfo;

import java.util.List;

/**
 * @author lixin
 * @creat 2019-08-13 下午05:18
 */
public interface ServerInfoManager {
    ServerInfo findServerById(Integer id);

    List<ServerInfo> findAllServerInfo();

    void saveServerInfo(ServerInfo serverInfo);

    void updateServerInfo(ServerInfo serverInfo);

    void deleteServerInfoById(Integer id);

    List<String> getAllServerInfo();

    ServerInfo findServerInfoByApiUrl(String apiUrl);
}
