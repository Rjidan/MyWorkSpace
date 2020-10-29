package com.imooc.mysell.service.impl;

import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.pojo.OrderMaster;
import com.imooc.mysell.repository.OrderMasterRepository;
import com.imooc.mysell.service.BuyerService;
import com.imooc.mysell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lzj
 * @Date 2020/9/29 11:44
 * @Version 1.0
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderMasterDTO findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderMasterDTO cancelOrder(String openid, String orderId) {

        OrderMasterDTO orderMasterDTO = checkOrderOwner(openid, orderId);
        if (orderMasterDTO == null) {
            log.error("[取消订单] 订单不存在, orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderMasterService.cancelOrder(orderMasterDTO);
    }

    private OrderMasterDTO checkOrderOwner(String openid, String orderId) {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        if (orderMasterDTO == null) {
            return null;
        }

        if (!orderMasterDTO.getBuyerOpenid().equals(openid)) {
            log.error("[查询订单] 订单的openid不一致,openid={},orderId={}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderMasterDTO;
    }
}
