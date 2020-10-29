package com.imooc.mysell.service;

import com.imooc.mysell.dto.OrderMasterDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @Author lzj
 * @Date 2020/9/30 14:16
 * @Version 1.0
 */
public interface PayService {

    PayResponse create(OrderMasterDTO orderMasterDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderMasterDTO orderDTO);
}
