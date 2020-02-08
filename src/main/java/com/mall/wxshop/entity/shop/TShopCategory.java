package com.mall.wxshop.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mall.entity.domain.BaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TShopCategory extends BaseEntity {
    private String shopId;
    private String categoryName;
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String isTouchMove;
}
