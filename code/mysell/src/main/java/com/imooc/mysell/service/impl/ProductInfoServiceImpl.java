package com.imooc.mysell.service.impl;

import com.imooc.mysell.dto.CartDTO;
import com.imooc.mysell.enums.ProductStatusEnum;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import com.imooc.mysell.pojo.ProductInfo;
import com.imooc.mysell.repository.ProductInfoRepository;
import com.imooc.mysell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author lzj
 * @Date 2020/9/22 19:57
 * @Version 1.0
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public Optional<ProductInfo> findProductInfoById(String productId) {
        return repository.findById(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Deprecated
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return repository.findByProductStatus(productStatus);
    }

    @Override
    public String save(ProductInfo productInfo) {
        return repository.save(productInfo).getProductId();
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO : cartDTOS) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId())
                    .orElseThrow(new SellException(ResultEnum.PRODUCT_NOT_EXIST));

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO : cartDTOS) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId())
                    .orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {

        ProductInfo productInfo = repository.findById(productId)
                .orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));

        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());

        ProductInfo info = repository.save(productInfo);

        return info;
    }

    /**
     * 下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {

        ProductInfo productInfo = repository.findById(productId)
                .orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));

        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());

        ProductInfo info = repository.save(productInfo);

        return info;
    }
}
