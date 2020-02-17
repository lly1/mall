package com.mall.wxshop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.wxshop.entity.base.WxBaseEntity;
import com.mall.wxshop.entity.sale.TCart;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TShopProduct extends WxBaseEntity {
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
    @TableField(exist = false)
    private String isTouchMove;
    /**
     * 跟商品有关的购物车
     */
    @TableField(exist = false)
    private TCart cart;
}
