package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.SellerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author lzj
 * @Date 2020/10/7 21:09
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {


    @Autowired
    SellerInfoRepository sellerInfoRepository;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();

        sellerInfo.setId("2");
        sellerInfo.setOpenid("abc");
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");

        sellerInfoRepository.save(sellerInfo);
    }

    @Test
    public void findByOpenid() {
        SellerInfo byOpenid = sellerInfoRepository.findByOpenid("123");
        System.out.println(byOpenid);
    }
}