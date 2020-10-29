package com.imooc.mysell.vo;

import lombok.Data;

import java.util.List;

/**
 * http请求返回的最外层对象
 * @Author lzj
 * @Date 2020/9/23 0:39
 * @Version 1.0
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
