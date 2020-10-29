package com.imooc.mysell.utils;

import com.imooc.mysell.enums.CodeEnum;

/**
 * @Author lzj
 * @Date 2020/10/7 11:58
 * @Version 1.0
 */
public class EnumUtils {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T e : enumClass.getEnumConstants()){
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }


}
