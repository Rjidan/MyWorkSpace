package com.imooc.mysell.service.impl;

import com.imooc.mysell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.mysell.dto.CartDTO;
import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.enums.OrderStatusEnum;
import com.imooc.mysell.enums.PayStatusEnum;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.pojo.OrderDetail;
import com.imooc.mysell.pojo.OrderMaster;
import com.imooc.mysell.pojo.ProductInfo;
import com.imooc.mysell.repository.OrderDetailRepository;
import com.imooc.mysell.repository.OrderMasterRepository;
import com.imooc.mysell.repository.ProductInfoRepository;
import com.imooc.mysell.service.*;
import com.imooc.mysell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author lzj
 * @Date 2020/9/24 17:20
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 新订单
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) {
        // 总价
        BigDecimal orderAmount = new BigDecimal(0);

        // 新增订单的逻辑
        String orderUniqueKey = KeyUtils.getUniqueKey();

        // 1.查询商品（数量，价格）
        for (OrderDetail orderDetail : orderMasterDTO.getOrderDetailList()) {

            ProductInfo productInfo = productInfoService
                    .findProductInfoById(orderDetail.getProductId())
                    .orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));

            // 2.计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            // 3.写入订单数据库（orderMaster, orderDetail）
            BeanUtils.copyProperties(productInfo,orderDetail);

            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetail.setOrderId(orderUniqueKey);

            orderDetailRepository.save(orderDetail);

        }

        // 3.写入订单数据库（orderMaster, orderDetail）
        OrderMaster orderMaster = new OrderMaster();

        BeanUtils.copyProperties(orderMasterDTO,orderMaster);

        orderMasterDTO.setOrderId(orderUniqueKey);
        orderMaster.setOrderId(orderUniqueKey);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);

        // 4.下单成功要扣库存
        List<CartDTO> cartDTOList = orderMasterDTO.getOrderDetailList().stream()
                .map(orderDetail ->
                    new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        // 新订单发送消息给后台
        webSocket.sendMessage(orderMasterDTO.getOrderId());

        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository
                .findById(orderId)
                .orElse(null);
        //TODO
        if (orderMaster == null){
            return null;
        }

        List<OrderDetail> details = orderDetailRepository
                .findByOrderId(orderMaster.getOrderId());
        if(details.isEmpty()){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        BeanUtils.copyProperties(orderMaster,orderMasterDTO);
        orderMasterDTO.setOrderDetailList(details);

        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> findAllByOpenid(String buyerOpenId, Pageable pageable) {

        Page<OrderMaster> orderMasters = orderMasterRepository
                .findByBuyerOpenid(buyerOpenId, pageable);

        List<OrderMasterDTO> orderMasterDTOS = OrderMaster2OrderDTOConverter
                .convert(orderMasters.getContent());

        return new PageImpl<OrderMasterDTO>(orderMasterDTOS, pageable, orderMasters.getTotalElements());

    }

    /**
     * 查询所有
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderMasterDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderMasterDTO> orderMasterDTOS = OrderMaster2OrderDTOConverter
                .convert(orderMasterPage.getContent());

        return new PageImpl<>(orderMasterDTOS, pageable, orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单（新订单->取消）
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO cancelOrder(OrderMasterDTO orderMasterDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);

        // 判断订单状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={}",
                    orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("[取消订单] 更新订单状态失败， orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }

        // 返还库存
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中没有商品详细，orderMasterDTO={}",orderMasterDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderMasterDTO.getOrderDetailList().stream()
                .map(orderDetail ->
                        new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        // 如果已经支付，需要退款
        if (orderMasterDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderMasterDTO);
        }
        return orderMasterDTO;
    }

    /**
     * 完结订单(新订单->完结)
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO finishOrder(OrderMasterDTO orderMasterDTO) {

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        // 判断订单状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单] 订单状态不正确，orderId={},orderStatus={}",
                    orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("[完结订单] 更新订单状态失败， orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }

        // 推送微信模板信息
        pushMessageService.orderStatus(orderMasterDTO);

        return orderMasterDTO;
    }

    /**
     * 支付订单(新订单->支付)
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO payOrder(OrderMasterDTO orderMasterDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);

        // 判断订单状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[支付订单] 订单状态不正确，orderId={},orderStatus={}",
                    orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if(!orderMasterDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[支付订单] 支付状态不正确，orderId={},payStatus={}",
                    orderMasterDTO.getOrderId(),orderMasterDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDERPAID_STATUS_ERROR);
        }

        // 修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMasterDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("[支付订单] 更新订单支付状态失败， orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_FAIL);
        }

        return orderMasterDTO;
    }
}
