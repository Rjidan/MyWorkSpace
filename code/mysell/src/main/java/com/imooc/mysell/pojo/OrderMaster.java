package com.imooc.mysell.pojo;

import com.imooc.mysell.enums.OrderStatusEnum;
import com.imooc.mysell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * @Author lzj
 * @Date 2020/9/24 15:34
 * @Version 1.0
 */
@Entity
@Data
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /** 默认是 0， 新订单.*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 默认是 0， 未支付.*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
}
