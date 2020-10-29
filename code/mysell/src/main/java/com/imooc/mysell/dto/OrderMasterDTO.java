package com.imooc.mysell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.mysell.enums.OrderStatusEnum;
import com.imooc.mysell.enums.PayStatusEnum;
import com.imooc.mysell.pojo.OrderDetail;
import com.imooc.mysell.utils.EnumUtils;
import com.imooc.mysell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/24 17:14
 * @Version 1.0
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderMasterDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /** 默认是 0， 新订单.*/
    private Integer orderStatus;

    /** 默认是 0， 未支付.*/
    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtils.getByCode(payStatus, PayStatusEnum.class);
    }
}
