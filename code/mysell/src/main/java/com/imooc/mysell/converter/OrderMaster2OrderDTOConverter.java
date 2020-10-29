package com.imooc.mysell.converter;

import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.pojo.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/28 16:23
 * @Version 1.0
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderMasterDTO convert(OrderMaster orderMaster){
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        BeanUtils.copyProperties(orderMaster,orderMasterDTO);

        return orderMasterDTO;
    }

    public static List<OrderMasterDTO> convert(List<OrderMaster> orderMasterList){
        List<OrderMasterDTO> orderMasterDTOS = new ArrayList<>();

        for (OrderMaster orderMaster : orderMasterList) {
            orderMasterDTOS.add(convert(orderMaster));
        }

        return orderMasterDTOS;
    }
}
