package com.mall.wxshop.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.wxshop.entity.base.WxBaseEntity;
import lombok.Data;

import java.util.List;


/**
 * @author lly
 */
@Data
public class TShop extends WxBaseEntity {
    private String userId;
    private String shopName;
    private String shopLogo;
    private String shopInfo;
    private Integer shopSale;
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Double shopStar;
    private String shopState;
    private String openTime;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    private String address;

    @TableField(exist = false)
    private String distance;

    @TableField(exist = false)
    //显示null的json
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private List<TShopCategory> tShopCategory;

    @TableField(exist = false)
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private List<TShopProduct> tShopProduct;
}
