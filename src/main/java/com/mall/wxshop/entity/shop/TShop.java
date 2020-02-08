package com.mall.wxshop.entity.shop;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.entity.domain.BaseEntity;
import lombok.Data;

import java.util.List;


/**
 * @author lly
 */
@Data
public class TShop extends BaseEntity {
    private String userId;
    private String shopName;
    private String shopLogo;
    private String shopInfo;
    private Double shopStar;
    private String shopState;
    private String openTime;
    //经度
    private String longitude;
    //纬度
    private String latitude;

    @TableField(exist = false)
    //显示null的json
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private List<TShopCategory> tShopCategory;

    @TableField(exist = false)
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private List<TShopProduct> tShopProduct;
}
