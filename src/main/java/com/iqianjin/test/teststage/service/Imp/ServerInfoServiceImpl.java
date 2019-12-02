package com.iqianjin.test.teststage.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.serverInfo.ServerInfoListDTO;
import com.iqianjin.test.teststage.dto.serverInfo.ServerInfoSaveDTO;
import com.iqianjin.test.teststage.entity.ServerInfo;
import com.iqianjin.test.teststage.manager.ServerInfoManager;
import com.iqianjin.test.teststage.service.ServerInfoService;
import com.iqianjin.test.teststage.vo.ServerInfoListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ServerInfoServiceImpl implements ServerInfoService {

    @Autowired
    private ServerInfoManager serverInfoManager;

    @Override
    public ServerInfo findServerInfoById(Integer id) {
        return serverInfoManager.findServerById(id);
    }

    @Override
    public ServerInfo findServerInfoByApiUrl(String apiUrl) {
        return serverInfoManager.findServerInfoByApiUrl(apiUrl);
    }

    @Override
    public Result saveServerInfo(ServerInfoSaveDTO serverInfoSaveDTO) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setApiUrl(serverInfoSaveDTO.getApiUrl());
        serverInfo.setDescription(serverInfoSaveDTO.getDescription());
        try {
            serverInfoManager.saveServerInfo(serverInfo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failMsg("保存失败");
        }
        return Result.success("保存成功！");
    }

    @Override
    public Result<ServerInfoListVO> findAllServerInfo(ServerInfoListDTO serverInfoListDTO) {
        PageHelper.startPage(serverInfoListDTO.getPageNum(), serverInfoListDTO.getPageSize());
        ServerInfoListVO serverInfoListVO = new ServerInfoListVO();
        try {
            List<ServerInfo> list = serverInfoManager.findAllServerInfo();
            PageInfo<ServerInfo> pageInfo = new PageInfo<>(list);
            serverInfoListVO.setServerInfoList(pageInfo);
            log.info("查找所有服务信息{}", list);
        }catch (Exception e){
            log.info("查找所有服务信息失败");
            e.printStackTrace();
            return Result.failMsg("获取所有服务的信息列表失败");
        }
        return Result.success(serverInfoListVO);
    }

    @Override
    public Result updateServerInfo(ServerInfo serverInfo) {
        log.info("开始修改id为{}的ServerInfo信息", serverInfo.getId());
        try {
            serverInfoManager.updateServerInfo(serverInfo);
            log.info("修改ServerInfo信息结束，{}", serverInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info("修改ServerInfo信息失败，{}", serverInfo);
            return Result.failMsg("编辑失败！");
        }
        return Result.success("编辑成功！");
    }

    @Override
    public Result deleteServerInfoById(Integer id) {
        log.info("开始删除id为{}的接口请求时的ip信息", id);
        try {
            serverInfoManager.deleteServerInfoById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.info("删除ServerInfo信息失败，id = {}", id);
            return Result.failMsg("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result getAllServerInfo() {
        List<String> list;
        try {
            list = serverInfoManager.getAllServerInfo();
        }catch (Exception e){
            e.printStackTrace();
            return Result.failMsg("获取失败");
        }
        return Result.success(list);
    }
}
