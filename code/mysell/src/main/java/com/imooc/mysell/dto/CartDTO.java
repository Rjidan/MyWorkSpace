package com.imooc.mysell.dto;

import lombok.Data;

/**
 * 购物车
 * @Author lzj
 * @Date 2020/9/24 21:34
 * @Version 1.0
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
