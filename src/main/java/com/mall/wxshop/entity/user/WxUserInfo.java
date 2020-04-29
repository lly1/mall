package com.mall.wxshop.entity.user;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.entity.user.UserRole;
import com.mall.wxshop.entity.base.WxBaseEntity;
import com.mall.wxshop.entity.shop.TShop;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * Created by lly on 2019/12/2.
 * 类名: WxUserInfo </br>
 */
@Data
@TableName("sys_wx_user")
public class WxUserInfo extends WxBaseEntity implements Serializable {
    private static final long serialVersionUID = -5671613769786329491L;
    //用户唯一编码
    @TableField("unionId")
    private String unionId;
    // 用户标识
    @TableField("openId")
    private String openId;
    // 用户昵称
    @TableField("nickName")
    private String nickName;
    // 性别（1是男性，2是女性，0是未知）
    @TableField("gender")
    private String gender;
    // 国家
    @TableField("country")
    private String country;
    // 省份
    @TableField("province")
    private String province;
    // 城市
    @TableField("city")
    private String city;
    // 用户头像链接
    @TableField("avatarUrl")
    private String avatarUrl;
    @TableField("phone")
    private String phone;
    //支付密码
    @TableField("payPass")
    private String payPass;
    // 用户角色
    @TableField(exist = false)
    private List<UserRole> roles;

    // 用户最大角色
    @TableField(exist = false)
    private String roleId;
    /**
     * 未处理订单
     */
    @TableField(exist = false)
    private Integer newOrder;

    @TableField(exist = false)
    private TShop shop;
}
