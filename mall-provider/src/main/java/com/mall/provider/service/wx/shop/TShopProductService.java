package com.mall.provider.service.wx.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wx.entity.shop.TShopProduct;

import java.util.List;

/**
 * @author lly
 */
public interface TShopProductService extends IService<TShopProduct> {
    List<TShopProduct> getProductUserCart(String userId, String categoryId);
}
