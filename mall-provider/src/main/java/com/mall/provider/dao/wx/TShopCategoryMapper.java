package com.mall.provider.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wx.entity.shop.TShopCategory;

import java.util.List;

public interface TShopCategoryMapper extends BaseMapper<TShopCategory> {
    List<TShopCategory> getShopCategory(String shopId);
    List<TShopCategory> getShopCategorySale(String shopId);
}
