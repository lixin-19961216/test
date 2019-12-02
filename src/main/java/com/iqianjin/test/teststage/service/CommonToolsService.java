package com.iqianjin.test.teststage.service;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.tools.CreateInterestDTO;
import com.iqianjin.test.teststage.dto.tools.CreateRedBagDTO;
import com.iqianjin.test.teststage.dto.tools.SendInterestDTO;
import com.iqianjin.test.teststage.dto.tools.SendRedBagDTO;

public interface CommonToolsService {
    /**
     * 创建普通红包
     * @param createRedBagDTO
     * @return
     */
    Result createRedBag(CreateRedBagDTO createRedBagDTO);

    /**
     * 创建加息券
     * @param createInterestDTO
     * @return
     */
    Result createInterest(CreateInterestDTO createInterestDTO);

    /**
     * 发送红包接口
     * @param sendRedBagDTO
     * @return
     */
    Result sendRedBag(SendRedBagDTO sendRedBagDTO);

    /**
     * 发送加息券接口
     * @param sendInterestDTO
     * @return
     */
    Result sendInterest(SendInterestDTO sendInterestDTO);
}
