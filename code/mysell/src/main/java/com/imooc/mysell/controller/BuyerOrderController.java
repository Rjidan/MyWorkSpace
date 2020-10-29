package com.imooc.mysell.controller;

import com.google.gson.Gson;
import com.imooc.mysell.converter.OrderForm2OrderMasterDTOConverter;
import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.form.OrderForm;
import com.imooc.mysell.service.BuyerService;
import com.imooc.mysell.service.OrderMasterService;
import com.imooc.mysell.utils.ResultVoUtils;
import com.imooc.mysell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @Author lzj
 * @Date 2020/9/28 18:03
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderMasterService orderMasterService;

    @Autowired
    BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> creat(@Valid OrderForm orderForm,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("[创建订单] 创建订单参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderMasterDTO orderMasterDTO = OrderForm2OrderMasterDTOConverter
                .convert(orderForm);

        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空，orderDetail={}", orderForm.getItems());
            throw new SellException(ResultEnum.CART_EMPTY_ERROR);
        }
//        orderMasterDTO.setBuyerOpenid("oTgZpwUD66sISh2XmmmbOyv5LQ1I");
        OrderMasterDTO masterDTO = orderMasterService.create(orderMasterDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",masterDTO.getOrderId());

        //[{orderId=1601293185054373976}]
        return ResultVoUtils.success(map);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderMasterDTO>> list(@RequestParam("openid") String openid,
                                                @RequestParam(value = "page",defaultValue = "0") Integer page,
                                                @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        Page<OrderMasterDTO> masterDTOS = orderMasterService
                .findAllByOpenid(openid, PageRequest.of(page, size));

        return ResultVoUtils.success(masterDTOS.getContent());
    }

    // 订单详情
    @GetMapping("/detail")
    public ResultVO<List<OrderMasterDTO>> detail(@RequestParam("openid") String openid,
                                                 @RequestParam("orderId") String orderId){

        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单详情] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(orderId)) {
            log.error("[查询订单详情] orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderMasterDTO orderMasterDTO = buyerService.findOrderOne(openid,orderId);

        return ResultVoUtils.success(orderMasterDTO);
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){

        if (StringUtils.isEmpty(openid)) {
            log.error("[取消订单] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(orderId)) {
            log.error("[取消订单] orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        buyerService.cancelOrder(openid, orderId);

        return ResultVoUtils.success();
    }

}
