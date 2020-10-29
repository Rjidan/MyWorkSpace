package com.imooc.mysell.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.mysell.enums.ProductStatusEnum;
import com.imooc.mysell.utils.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * @Author lzj
 * @Date 2020/9/22 19:20
 * @Version 1.0
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    private String productDescription;

    private String productIcon;

    /** 商品状态,0正常1下架 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtils.getByCode(productStatus, ProductStatusEnum.class);
    }

}
