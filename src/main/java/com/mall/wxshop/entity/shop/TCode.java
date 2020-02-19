package com.mall.wxshop.entity.shop;

import com.mall.wxshop.entity.base.WxBaseEntity;
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
