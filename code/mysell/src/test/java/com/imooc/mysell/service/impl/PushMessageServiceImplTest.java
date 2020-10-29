package com.imooc.mysell.service.impl;

import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.service.OrderMasterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author lzj
 * @Date 2020/10/13 11:43
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    public PushMessageServiceImpl pushMessageService;

    @Autowired
    public OrderMasterService service;

    @Test
    public void orderStatus() {
        OrderMasterDTO one = service.findOne("1602314322122262108");

        pushMessageService.orderStatus(one);

    }
}