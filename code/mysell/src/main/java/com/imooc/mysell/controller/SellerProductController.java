package com.imooc.mysell.controller;

import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.form.ProductForm;
import com.imooc.mysell.pojo.ProductCategory;
import com.imooc.mysell.pojo.ProductInfo;
import com.imooc.mysell.service.ProductCategoryService;
import com.imooc.mysell.service.ProductInfoService;
import com.imooc.mysell.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author lzj
 * @Date 2020/10/7 18:17
 * @Version 1.0
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    private final ProductInfoService productInfoService;

    private final ProductCategoryService productCategoryService;

    @Autowired
    public SellerProductController(ProductInfoService productInfoService,
                                   ProductCategoryService productCategoryService) {
        this.productInfoService = productInfoService;
        this.productCategoryService = productCategoryService;
    }

    /**
     * 商品列表
     * @param page 第几页
     * @param size 一页有多少数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){

        Page<ProductInfo> productInfos = productInfoService
                .findAll(PageRequest.of(page - 1, size));

        map.put("productInfos",productInfos);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("product/list",map);
    }


    /**
     * 修改/新增商品
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                              Map<String, Object> map){

        if (!StringUtils.isEmpty(productId)) {
            Optional<ProductInfo> info = productInfoService.findProductInfoById(productId);
            if (info.isPresent()){
                map.put("productInfo",info.get());
            }else {
                map.put("productInfo",new ProductInfo());
            }

        }else {
            map.put("productInfo",new ProductInfo());
        }

        List<ProductCategory> categoryList = productCategoryService.findAll();

        map.put("categoryList",categoryList);

        return new ModelAndView("product/index",map);
    }

    /**
     * 保存商品
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        try {
            ProductInfo productInfo = new ProductInfo();

            if (StringUtils.isEmpty(productForm.getProductId())) {
                // 新增
                productForm.setProductId(KeyUtils.getUniqueKey());
            }else {
                // 修改
                productInfo = productInfoService
                        .findProductInfoById(productForm.getProductId())
                        .orElseThrow(new SellException(ResultEnum.PRODUCT_NOT_EXIST));
            }

            BeanUtils.copyProperties(productForm, productInfo);

            productInfoService.save(productInfo);

            map.put("msg", ResultEnum.PRODUCT_ADD_SUCCESS.getMsg());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/success",map);

        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

    }


    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){

        try {

            productInfoService.onSale(productId);

        }catch (SellException e){

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnum.PRODUCT_ON_SALE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){
        try {

            productInfoService.offSale(productId);

        }catch (SellException e){

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ResultEnum.PRODUCT_OFF_SALE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
