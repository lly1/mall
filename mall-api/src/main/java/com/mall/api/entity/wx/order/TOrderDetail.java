package com.mall.api.entity.wx.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.api.entity.base.WxBaseEntity;
import com.mall.api.entity.wx.shop.TShopProduct;
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
