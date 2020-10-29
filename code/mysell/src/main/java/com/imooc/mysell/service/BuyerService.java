package com.imooc.mysell.service;

import com.imooc.mysell.dto.OrderMasterDTO;

/**
 * @Author lzj
 * @Date 2020/9/29 11:41
 * @Version 1.0
 */
public interface BuyerService {

    /** 查询一个订单 */
    OrderMasterDTO findOrderOne(String openid, String orderId);

    /** 取消订单 */
    OrderMasterDTO cancelOrder(String openid, String orderId);
}
