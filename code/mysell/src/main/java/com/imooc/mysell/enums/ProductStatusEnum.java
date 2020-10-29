package com.imooc.mysell.enums;

import lombok.Data;
import lombok.Getter;

import java.awt.print.Pageable;

/**
 * 商品状态
 * @Author lzj
 * @Date 2020/9/22 20:04
 * @Version 1.0
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"在架"),
    DOWN(1,"下架");


    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getMessage(){
        return msg;
    }
}
