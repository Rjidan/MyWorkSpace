package com.imooc.mysell.service.impl;

import com.imooc.mysell.enums.ProductStatusEnum;
import com.imooc.mysell.pojo.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 20:18
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findProductInfoById() {
//        ProductInfo productInfoById = service.findProductInfoById("12");
//        System.out.println(productInfoById);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = service.findUpAll();
        System.out.println(upAll);
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<ProductInfo> all = service.findAll(pageRequest);
        System.out.println(all);

    }

    @Test
    public void findByProductStatus() {
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1221");
        productInfo.setProductName("service2");
        productInfo.setProductPrice(new BigDecimal(3.0));
        productInfo.setProductStock(11);
        productInfo.setProductDescription("ok");
        productInfo.setProductIcon("http://xxx.com");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(3);
        String save = service.save(productInfo);
        System.out.println(save);
    }
}