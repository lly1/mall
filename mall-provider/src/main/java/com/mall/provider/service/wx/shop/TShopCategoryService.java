package com.mall.provider.service.wx.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wx.entity.shop.TShopCategory;

import java.util.List;

/**
 * @author lly
 */
public interface TShopCategoryService extends IService<TShopCategory> {
   List<TShopCategory> getShopCategory(String shopId);
   List<TShopCategory> getShopCategorySale(String shopId);
}
