package com.mall.wxshop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.shop.TShop;

/**
 * @author lly
 */
public interface TShopService extends IService<TShop> {
    TShop getShop(String userId);
}
