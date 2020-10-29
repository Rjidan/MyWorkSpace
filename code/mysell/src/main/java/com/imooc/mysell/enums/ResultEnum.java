package com.imooc.mysell.enums;

import lombok.Getter;

/**
 * @Author lzj
 * @Date 2020/9/24 20:42
 * @Version 1.0
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),

    PARAM_ERROR(1,"参数不正确"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详细不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_STATUS_UPDATE_FAIL(15,"更新订单状态失败"),
    ORDER_DETAIL_EMPTY(16,"订单中没有商品详细"),
    ORDERPAID_STATUS_ERROR(17,"订单支付状态不正确"),
    CART_EMPTY_ERROR(18,"购物车为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    WECHAT_MP_ERROR(20,"微信公众号方面出错"),

    ORDER_CANCEL_SUCCESS(22,"订单取消成功"),
    ORDER_FINISH_SUCCESS(23,"订单完结成功"),
    PRODUCT_ADD_SUCCESS(24,"新增商品成功"),
    PRODUCT_ON_SALE_SUCCESS(25,"商品上架成功"),
    PRODUCT_OFF_SALE_SUCCESS(26,"商品下架成功"),


    PRODUCT_STATUS_ERROR(27,"商品状态不正确"),
    CATEGORY_ADD_SUCCESS(28,"新增类目成功"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(29, "微信支付异步通知金额校验不通过"),
    LOGIN_FAIL(30, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(31, "登出成功")
    ;


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
