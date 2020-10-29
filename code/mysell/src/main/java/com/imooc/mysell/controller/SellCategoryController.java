package com.imooc.mysell.controller;

import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.form.CategoryForm;
import com.imooc.mysell.form.ProductForm;
import com.imooc.mysell.pojo.ProductCategory;
import com.imooc.mysell.pojo.ProductInfo;
import com.imooc.mysell.service.ProductCategoryService;
import com.imooc.mysell.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author lzj
 * @Date 2020/10/7 20:07
 * @Version 1.0
 */
@Controller
@RequestMapping("/seller/category")
public class SellCategoryController {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public SellCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){

        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("category/list", map);
    }


    /**
     * 修改/新增商品类目
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                              Map<String, Object> map){

        // 修改
        if (!StringUtils.isEmpty(categoryId)) {
            ProductCategory category = productCategoryService.findByCategoryId(categoryId);
            if (category != null){
                map.put("category", category);
            }else {
                map.put("category",new ProductCategory());
            }

        }else {
            map.put("category",new ProductCategory());
        }

        return new ModelAndView("category/index",map);
    }


    /**
     * 保存商品类目
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        try {
            ProductCategory category = new ProductCategory();

            if (StringUtils.isEmpty(categoryForm.getCategoryId())) {
                // 新增
            }else {
                // 修改
                category = productCategoryService
                        .findByCategoryId(categoryForm.getCategoryId());
            }

            BeanUtils.copyProperties(categoryForm, category);

            productCategoryService.save(category);

            map.put("msg", ResultEnum.CATEGORY_ADD_SUCCESS.getMsg());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/success",map);

        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }
    }
}
