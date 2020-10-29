package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/24 16:54
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    @Test
    public void findByOrderId() {
        List<OrderDetail> byOrderId = repository.findByOrderId("2");
        System.out.println(byOrderId);

    }

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("11");
        orderDetail.setOrderId("2");
        orderDetail.setProductId("111");
        orderDetail.setProductName("service");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductQuantity(3);

        repository.save(orderDetail);
    }
}