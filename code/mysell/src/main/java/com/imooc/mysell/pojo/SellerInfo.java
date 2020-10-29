package com.imooc.mysell.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author lzj
 * @Date 2020/10/7 21:05
 * @Version 1.0
 */
@Entity
@Data
public class SellerInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;


}
