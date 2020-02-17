package com.mall.wxshop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.shop.TShopProduct;

import java.util.List;

/**
 * @author lly
 */
public interface TShopProductService extends IService<TShopProduct> {
    List<TShopProduct> getProductUserCart(String userId, String categoryId);
}
