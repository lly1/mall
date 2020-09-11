package com.mall.provider.service.wx.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.wx.shop.TShop;

/**
 * @author lly
 */
public interface TShopService extends IService<TShop> {
    TShop getShop(String userId);
}
