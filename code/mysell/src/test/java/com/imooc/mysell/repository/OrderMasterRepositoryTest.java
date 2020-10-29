package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @Author lzj
 * @Date 2020/9/24 16:11
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;

    @Test
    public void findByOpenId(){

        Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid("1234", PageRequest.of(0, 2));

        System.out.println(byBuyerOpenid);
    }

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("3");
        orderMaster.setBuyerName("小张");
        orderMaster.setBuyerAddress("北京");
        orderMaster.setBuyerPhone("11111111111111");
        orderMaster.setOrderAmount(new BigDecimal(3.3));
        orderMaster.setBuyerOpenid("12345");

        repository.save(orderMaster);
    }


}