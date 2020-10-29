package com.imooc.mysell.service.impl;

import com.imooc.mysell.pojo.ProductCategory;
import com.imooc.mysell.repository.ProductCategoryRepository;
import com.imooc.mysell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lzj
 * @Date 2020/9/22 12:13
 * @Version 1.0
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repsitory;

    @Override
    public ProductCategory findByCategoryId(Integer id) {
        return repsitory.findById(id).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repsitory.findAll();
    }

    @Override
    public List<ProductCategory> findAllByCategoryTypeListIn(List categoryTypeList) {
        return repsitory.findAllByCategoryTypeIn(categoryTypeList);
    }


    @Override
    public Integer save(ProductCategory productCategory) {
        return repsitory.save(productCategory).getCategoryId();
    }
}
