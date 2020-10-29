package com.imooc.mysell.service;

import com.imooc.mysell.dto.CartDTO;
import com.imooc.mysell.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @Author lzj
 * @Date 2020/9/22 19:48
 * @Version 1.0
 */
public interface ProductInfoService {

    Optional<ProductInfo> findProductInfoById(String productId);

    /** 所有上架的商品 */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    List<ProductInfo> findByProductStatus(Integer productStatus);

    String save(ProductInfo productInfo);

    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOS);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOS);

    /** 上架 */
    ProductInfo onSale(String productId);

    /** 下架 */
    ProductInfo offSale(String productId);

}
