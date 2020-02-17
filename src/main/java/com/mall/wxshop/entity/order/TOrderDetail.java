package com.mall.wxshop.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.wxshop.entity.base.WxBaseEntity;
import com.mall.wxshop.entity.shop.TShopProduct;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TOrderDetail extends WxBaseEntity {
    private String orderId;
    private String productId;
    private Integer buyNum;
    @TableField(exist = false)
    private TShopProduct product;
}
