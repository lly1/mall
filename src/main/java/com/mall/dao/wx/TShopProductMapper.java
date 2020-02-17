package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.shop.TShopProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TShopProductMapper extends BaseMapper<TShopProduct> {
    List<TShopProduct> getProductUserCart(@Param("userId")String userId,@Param("categoryId")String categoryId);
}
