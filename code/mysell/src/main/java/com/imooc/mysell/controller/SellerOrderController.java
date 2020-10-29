package com.imooc.mysell.controller;

import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author lzj
 * @Date 2020/10/7 11:11
 * @Version 1.0
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    private final OrderMasterService orderMasterService;

    @Autowired
    public SellerOrderController(OrderMasterService service) {
        this.orderMasterService = service;
    }


    /**
     * 订单列表
     * @param page 第几页
     * @param size 一页有多少数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){

        Page<OrderMasterDTO> orderMasterDTOS = orderMasterService
                .findAll(PageRequest.of(page - 1, size));

        map.put("orderMasterDTOS",orderMasterDTOS);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("order/list",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        try{

        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);

        if (orderMasterDTO == null){
            log.error("[卖家取消订单] 订单不存在 orderId={}", orderId);

            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMsg());
            map.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", map);
        }

        orderMasterService.cancelOrder(orderMasterDTO);

        }catch (SellException e){
            log.error("[卖家取消订单] 发生异常 orderId={}", orderId);

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){

        OrderMasterDTO orderMasterDTO = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderId);

            if (orderMasterDTO == null){
                log.error("[卖家查询订单详情] 订单不存在 orderId={}", orderId);

                map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMsg());
                map.put("url", "/sell/seller/order/list");

                return new ModelAndView("common/error", map);
            }

        }catch (SellException e){
            log.error("[卖家查询订单详情] 发生异常 orderId={}", orderId);

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", map);
        }

        map.put("orderMasterDTO",orderMasterDTO);

        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        OrderMasterDTO orderMasterDTO = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderId);

            if (orderMasterDTO == null){
                log.error("[卖家完结订单] 订单不存在 orderId={}", orderId);

                map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMsg());
                map.put("url", "/sell/seller/order/list");

                return new ModelAndView("common/error", map);
            }

            orderMasterService.finishOrder(orderMasterDTO);

        }catch (SellException e){
            log.error("[卖家完结订单] 发生异常 orderId={}", orderId);

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }

}
