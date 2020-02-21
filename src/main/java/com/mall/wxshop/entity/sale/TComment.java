package com.mall.wxshop.entity.sale;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.wxshop.entity.base.WxBaseEntity;
import com.mall.wxshop.entity.user.WxUserInfo;
import lombok.Data;

@Data
public class TComment extends WxBaseEntity {
    private String userId;
    private String shopId;
    private String orderId;
    private String content;
    private Double star;
    private String imgUrl;
    @TableField(exist = false)
    @JSONField(serialzeFeatures = SerializerFeature.DisableCircularReferenceDetect)
    private WxUserInfo user;
}
