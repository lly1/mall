package com.mall.provider.dao.component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.wx.component.TProductComponent;

import java.util.List;


public interface TProductComponentMapper extends BaseMapper<TProductComponent> {
    List<TProductComponent> findByProductId(String productId);
}
