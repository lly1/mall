package com.mall.wxshop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.entity.shop.TShopCategory;

import java.util.List;

/**
 * @author lly
 */
public interface TShopCategoryService extends IService<TShopCategory> {
   List<TShopCategory> getShopCategory(String shopId);
}
