package com.imooc.mysell.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author lzj
 * @Date 2020/10/7 21:18
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoServiceImplTest {

    @Autowired
    SellerInfoServiceImpl sellerInfoService;

    @Test
    public void findSellerInfoByOpenid() {
        System.out.println(sellerInfoService.findSellerInfoByOpenid("abc"));
    }
}