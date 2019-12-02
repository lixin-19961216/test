package com.iqianjin.test.teststage.manager.Imp;

import com.iqianjin.test.teststage.dao.ServerInfoMapper;
import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.manager.ServerInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServerInfoManagerImpl implements ServerInfoManager {
    @Autowired
    private ServerInfoMapper serverInfoMapper;

    @Override
    public ServerInfo findServerById(Integer id) {
        return serverInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ServerInfo> findAllServerInfo() {
        return serverInfoMapper.findAllServerInfo();
    }

    @Override
    public void saveServerInfo(ServerInfo serverInfo) {
        serverInfoMapper.insert(serverInfo);
    }

    @Override
    public void updateServerInfo(ServerInfo serverInfo) {
        serverInfoMapper.updateByPrimaryKeySelective(serverInfo);
    }

    @Override
    public void deleteServerInfoById(Integer id) {
        serverInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<String> getAllServerInfo() {
        return serverInfoMapper.getAllServerInfo();
    }

    @Override
    public ServerInfo findServerInfoByApiUrl(String apiUrl) {
        return serverInfoMapper.findServerInfoByApiUrl(apiUrl);
    }
}
