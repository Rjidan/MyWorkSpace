package com.imooc.mysell.service.impl;

import com.imooc.mysell.pojo.SellerInfo;
import com.imooc.mysell.repository.SellerInfoRepository;
import com.imooc.mysell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lzj
 * @Date 2020/10/7 21:17
 * @Version 1.0
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
