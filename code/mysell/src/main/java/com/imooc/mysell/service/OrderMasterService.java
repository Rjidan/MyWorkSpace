package com.imooc.mysell.service;

import com.imooc.mysell.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author lzj
 * @Date 2020/9/24 17:08
 * @Version 1.0
 */
public interface OrderMasterService {

    /** 创建订单 */
    OrderMasterDTO create(OrderMasterDTO orderMasterDTO);

    OrderMasterDTO findOne(String orderId);

    Page<OrderMasterDTO> findAllByOpenid(String buyerOpenId, Pageable pageable);

    Page<OrderMasterDTO> findAll(Pageable pageable);

    OrderMasterDTO cancelOrder(OrderMasterDTO orderMasterDTO);

    OrderMasterDTO finishOrder(OrderMasterDTO orderMasterDTO);

    OrderMasterDTO payOrder(OrderMasterDTO orderMasterDTO);

}
