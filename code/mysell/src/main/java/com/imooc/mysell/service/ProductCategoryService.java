package com.imooc.mysell.service;

import com.imooc.mysell.pojo.ProductCategory;

import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 12:09
 * @Version 1.0
 */
public interface ProductCategoryService {

    ProductCategory findByCategoryId(Integer id);

    List<ProductCategory> findAll();

    List<ProductCategory> findAllByCategoryTypeListIn(List categoryTypeList);

    Integer save(ProductCategory productCategory);
}
