package com.imooc.mysell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author lzj
 * @Date 2020/9/24 15:41
 * @Version 1.0
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消");

    private Integer code;

    private String status;

    OrderStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getMessage(){
        return status;
    }
}
