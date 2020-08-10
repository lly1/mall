package com.mall.provider.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wx.entity.shop.TShop;

public interface TShopMapper extends BaseMapper<TShop> {
    TShop getShop(String userId);
}
