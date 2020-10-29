package com.imooc.mysell.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * @Author lzj
 * @Date 2020/9/28 18:08
 * @Version 1.0
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家微信
     */
    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
