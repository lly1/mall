package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.shop.TShopCategory;

import java.util.List;

public interface TShopCategoryMapper extends BaseMapper<TShopCategory> {
    List<TShopCategory> getShopCategory(String shopId);
}
