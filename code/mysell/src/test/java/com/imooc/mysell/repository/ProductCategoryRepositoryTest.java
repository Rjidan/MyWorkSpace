package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 11:00
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repsitory;

    @Test
    public void findOne(){
        ProductCategory productCategory = repsitory.findById(2).orElse(null);
        System.out.println(productCategory);
    }


    @Test
    public void save(){
        ProductCategory productCategory = new ProductCategory("d",3);
        repsitory.save(productCategory);
    }

    @Test
    public void findAllByCategoryTypeIn(){
        List categoryList = Arrays.asList(2,3,4,5,6);
        List<ProductCategory> allByCategoryTypeIn = repsitory.findAllByCategoryTypeIn(categoryList);
        System.out.println(allByCategoryTypeIn);
    }

}