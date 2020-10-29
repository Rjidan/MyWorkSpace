package com.imooc.mysell.form;

import lombok.Data;
import java.math.BigDecimal;

/**
 * @Author lzj
 * @Date 2020/10/7 19:32
 * @Version 1.0
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

}
