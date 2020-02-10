package com.mall.wxshop.entity.shop;

import com.mall.entity.domain.BaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TShopProduct extends BaseEntity {
    private String categoryId;
    private String shopId;
    private String productName;
    private String productPrice;
    private String productStock;
    private String productInfo;
    private String productIcon;
    /**
     * 0正常1下架
     */
    private String productStatus;
    private Integer saleTotal;
    private Integer starTotal;
    private String isTouchMove;
}
