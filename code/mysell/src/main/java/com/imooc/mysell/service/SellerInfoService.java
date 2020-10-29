package com.imooc.mysell.service;

import com.imooc.mysell.pojo.SellerInfo;

/**
 * @Author lzj
 * @Date 2020/10/7 21:16
 * @Version 1.0
 */
public interface SellerInfoService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
