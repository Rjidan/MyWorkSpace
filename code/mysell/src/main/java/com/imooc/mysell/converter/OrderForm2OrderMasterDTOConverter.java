package com.imooc.mysell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.form.OrderForm;
import com.imooc.mysell.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/28 18:40
 * @Version 1.0
 */
@Slf4j
public class OrderForm2OrderMasterDTOConverter {

    public static OrderMasterDTO convert(OrderForm orderForm){
        Gson gson = new Gson();

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        orderMasterDTO.setBuyerName(orderForm.getName());
        orderMasterDTO.setBuyerPhone(orderForm.getPhone());
        orderMasterDTO.setBuyerAddress(orderForm.getAddress());
        orderMasterDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> list = new ArrayList<>();
        try {
            list = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());

        }catch (Exception e){
            log.error("[对象转换] 出错，jsonStr={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderMasterDTO.setOrderDetailList(list);

        return orderMasterDTO;
    }
}
