package com.imooc.mysell.enums;

import lombok.Getter;

/**
 * @Author lzj
 * @Date 2020/9/24 15:47
 * @Version 1.0
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0,"未支付"),
    SUCCESS(1,"支付成功");


    private Integer code;

    private String status;

    PayStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getMessage(){
        return status;
    }
}
