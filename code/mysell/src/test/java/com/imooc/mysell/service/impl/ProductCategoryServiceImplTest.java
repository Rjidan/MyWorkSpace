package com.imooc.mysell.service.impl;

import com.imooc.mysell.pojo.ProductCategory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 12:18
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryServiceImplTest {

    @Autowired
    ProductCategoryServiceImpl service;

    @Test
    void findByCategoryId() {
        ProductCategory byCategoryId = service.findByCategoryId(2);
        System.out.println(byCategoryId);
    }

    @Test
    void findAll() {
        List<ProductCategory> all = service.findAll();
        System.out.println(all);
    }

    @Test
    void findAllByCategoryIdListIn() {

        List<ProductCategory> allByCategoryIdListIn = service.findAllByCategoryTypeListIn(Arrays.asList(3));
        System.out.println(allByCategoryIdListIn);
    }

    @Test
    void save() {
        service.save(new ProductCategory("5",2));
    }
}