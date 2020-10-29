package com.imooc.mysell.repository;

import com.imooc.mysell.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author lzj
 * @Date 2020/10/7 21:08
 * @Version 1.0
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenid(String openid);
}
