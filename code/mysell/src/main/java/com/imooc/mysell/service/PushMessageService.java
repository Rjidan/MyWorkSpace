package com.imooc.mysell.service;


import com.imooc.mysell.dto.OrderMasterDTO;

/**
 * 推送消息
 * Created by 廖师兄
 * 2017-07-30 22:08
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderMasterDTO orderDTO);
}
