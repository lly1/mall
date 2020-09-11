package com.mall.api.entity.wx.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.api.entity.base.WxBaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lly
 */
@Data
public class TShopCategory extends WxBaseEntity {
    private String shopId;
    private String categoryName;
    @TableField(exist = false)
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String isTouchMove;
    @TableField(exist = false)
    private List<TShopProduct> shopProducts;
}
