package com.imooc.mysell.service.impl;

import com.imooc.mysell.dto.OrderMasterDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author lzj
 * @Date 2020/9/30 17:55
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {

    @Autowired
    OrderMasterServiceImpl service;

    @Autowired
    private PayServiceImpl payService;

    @Test
    public void create() {
        OrderMasterDTO orderMasterDTO = service.findOne("1600961124150310021");
        payService.create(orderMasterDTO);
    }
}