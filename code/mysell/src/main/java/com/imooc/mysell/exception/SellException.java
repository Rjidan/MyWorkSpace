package com.imooc.mysell.exception;

import com.imooc.mysell.enums.ResultEnum;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * @Author lzj
 * @Date 2020/9/24 20:41
 * @Version 1.0
 */
@Getter
public class SellException extends RuntimeException implements Supplier {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String defaultMessage) {
        super(defaultMessage);

        this.code = code;
    }

    @Override
    public Object get() {
        return null;
    }
}
