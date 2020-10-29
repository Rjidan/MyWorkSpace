package com.imooc.mysell.service.impl;

import com.imooc.mysell.dto.OrderMasterDTO;
import com.imooc.mysell.pojo.OrderDetail;
import com.imooc.mysell.pojo.OrderMaster;
import com.imooc.mysell.repository.OrderDetailRepository;
import com.imooc.mysell.repository.OrderMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/24 21:57
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    OrderMasterServiceImpl service;

    @Autowired
    OrderMasterRepository repository;

    @Autowired
    OrderDetailRepository detailRepository;

    @Test
    public void create() {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        OrderMaster orderMaster = repository.findById("2").orElse(null);
        orderMaster.setOrderId("3");
        BeanUtils.copyProperties(orderMaster,orderMasterDTO);

        List<OrderDetail> byOrderId = detailRepository.findByOrderId("2");
        byOrderId.stream().forEach(orderDetail ->
        {
            orderDetail.setOrderId("1");
            orderDetail.setProductName("皮蛋粥");
        });

        orderMasterDTO.setOrderDetailList(byOrderId);

        OrderMasterDTO orderMasterDTO1 = service.create(orderMasterDTO);
        log.info("[创建订单] result={}", orderMasterDTO1);
    }

    @Test
    public void findOne() {
        OrderMasterDTO orderMasterDTO = service.findOne("1600961124150310021");
        System.out.println(orderMasterDTO);
    }

    @Test
    public void findAllByOpenid() {
        Page<OrderMasterDTO> orderMasterDTOS = service
                .findAllByOpenid("1234", PageRequest.of(0, 2));

        System.out.println(orderMasterDTOS.getContent());
    }

    @Test
    public void cancelOrder() {
        OrderMasterDTO orderMasterDTO = service.findOne("1600961124150310021");
        OrderMasterDTO cancelOrderMasterDTO = service.cancelOrder(orderMasterDTO);
        System.out.println(cancelOrderMasterDTO);
    }

    @Test
    public void finishOrder() {
        OrderMasterDTO orderMasterDTO = service.findOne("1600961161451997854");
        OrderMasterDTO cancelOrderMasterDTO = service.finishOrder(orderMasterDTO);
        System.out.println(cancelOrderMasterDTO);
    }

    @Test
    public void payOrder() {
        OrderMasterDTO orderMasterDTO = service.findOne("1600962330169460052");
        OrderMasterDTO cancelOrderMasterDTO = service.payOrder(orderMasterDTO);
        System.out.println(cancelOrderMasterDTO);
    }


    @Test
    public void findAll() {
        Page<OrderMasterDTO> orderMasterDTOS = service
                .findAll(PageRequest.of(0, 10));

        System.out.println(orderMasterDTOS.getContent());
    }
}