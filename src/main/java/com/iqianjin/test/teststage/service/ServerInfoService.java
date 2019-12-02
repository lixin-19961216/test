package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.serverInfo.ServerInfoListDTO;
import com.iqianjin.test.teststage.dto.serverInfo.ServerInfoSaveDTO;
import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.vo.ServerInfoListVO;

import java.util.List;

/**
 * 运行项目相关
 */
public interface ServerInfoService {
    /**
     * 通过id查找对应的项目相关信息
     * @param id
     * @return
     */
    ServerInfo findServerInfoById(Integer id);

    /**
     * 通过apiUrl查找对应的项目相关信息
     * @param apiUrl
     * @return
     */
    ServerInfo findServerInfoByApiUrl(String apiUrl);

    /**
     * 保存ServerInfo对象
     * @param serverInfoSaveDTO
     */
    Result saveServerInfo(ServerInfoSaveDTO serverInfoSaveDTO);

    /**
     * 获取所有ServerInfo信息
     * @return
     */
    Result<ServerInfoListVO> findAllServerInfo(ServerInfoListDTO serverInfoListDTO);

    /**
     * 修改已有的ServerInfo信息
     * @param serverInfo
     */
    Result updateServerInfo(ServerInfo serverInfo);

    /**
     * 根据id删除已有的接口请求时的ip信息
     * @param id
     * @return
     */
    Result deleteServerInfoById(Integer id);

    /**
     * 获取所有平台
     * @return
     */
    Result getAllServerInfo();
}
