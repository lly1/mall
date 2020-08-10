package com.mall.wx.entity.shop;

import com.mall.wx.entity.base.WxBaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TCode extends WxBaseEntity {
    private String shopId;
    //乐观锁，同时充当取货号
    private Integer code;;
}
