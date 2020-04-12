package com.mall.wxshop.entity.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mall.wxshop.entity.base.WxBaseEntity;
import com.mall.wxshop.entity.shop.TShop;
import lombok.Data;

import java.util.List;

/**
 * @author lly
 */
@Data
public class TOrder extends WxBaseEntity {
    private String shopId;
    /**
     * pc查询显示用
     */
    @TableField(exist = false)
    private String shopName;
    @TableField(exist = false)
    private String shopLogo;
    private String userId;
    /**
     * pc查询显示用
     */
    @TableField(exist = false)
    private String userName;
    private Integer rtnCode;
    private Double payTotal;
    private Integer buyTotal;
    private String remarks;
    /**
     * 是否评价
     */
    private String isRate;
    /**
     * 0已提交，1已支付 2商家接单，3已完成
     */
    private Integer orderStatus;
    @TableField(exist = false)
    @JSONField(serialzeFeatures = SerializerFeature.DisableCircularReferenceDetect)
    private TShop shop;
    @TableField(exist = false)
    private List<TOrderDetail> detailList;
    /**
     * 小程序左滑删除用
     */
    @TableField(exist = false)
    private String isTouchMove;
}
