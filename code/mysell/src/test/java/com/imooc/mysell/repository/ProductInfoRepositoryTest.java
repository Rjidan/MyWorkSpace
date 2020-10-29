package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 19:28
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;


    @Test
    public void findOne(){
        ProductInfo info = repository.findById("1").orElse(null);
        System.out.println(info);
    }

    @Test
    public void findAll(){
        List<ProductInfo> all = repository.findAll();
        System.out.println(all);
    }


    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("111");
        productInfo.setProductName("sxxs");
        productInfo.setProductPrice(new BigDecimal(3.0));
        productInfo.setProductStock(11);
        productInfo.setProductDescription("ok");
        productInfo.setProductIcon("http://xxx.com");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo info = repository.save(productInfo);
        System.out.println(info);
    }

    @Test
    public void findByProductStatus(){
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        System.out.println(byProductStatus);
    }
}