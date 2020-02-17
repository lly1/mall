package com.mall.wxshop.entity.sale;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.wxshop.entity.base.WxBaseEntity;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.entity.shop.TShopProduct;
import lombok.Data;

@Data
public class TCart extends WxBaseEntity {
    private String userId;
    private String shopId;
    private String productId;
    /**
     * 购买数量
     */
    private Integer buyNum;
    @TableField(exist = false)
    private TShop shop;
    @TableField(exist = false)
    private TShopProduct product;
}
