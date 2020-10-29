package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 10:59
 * @Version 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findAllByCategoryTypeIn(List categoryTypeList);
}
