package com.imooc.mysell.controller;

import com.imooc.mysell.pojo.ProductCategory;
import com.imooc.mysell.pojo.ProductInfo;
import com.imooc.mysell.service.ProductCategoryService;
import com.imooc.mysell.service.ProductInfoService;
import com.imooc.mysell.utils.ResultVoUtils;
import com.imooc.mysell.vo.ProductInfoVO;
import com.imooc.mysell.vo.ProductVO;
import com.imooc.mysell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @Author lzj
 * @Date 2020/9/23 0:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

        //1.查询所有上架的商品
        List<ProductInfo> productInfos = productService.findUpAll();

        //2.查询商品类目（一次性查询）
        List<Integer> categoryTypeList = productInfos.stream().map(
                productInfo -> productInfo.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategories = categoryService.findAllByCategoryTypeListIn(categoryTypeList);

        //3.数据拼接(ResultVO(data) --> productVOS(list) --> productInfoVOS(foods) --> productInfoVO)
        List<ProductVO> productVOList = new ArrayList<>();

        for (ProductCategory productCategory : productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());


            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            productInfos.stream().forEach(p -> {
                if (p.getCategoryType().equals(productCategory.getCategoryType())) {

                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(p,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            });

            productVO.setProductInfoVOList(productInfoVOList);

            productVOList.add(productVO);
        }

        return ResultVoUtils.success(productVOList);
    }
}
