package com.mall.dao.component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.entity.component.TProductComponent;

import java.util.List;


public interface TProductComponentMapper extends BaseMapper<TProductComponent> {
    List<TProductComponent> findByProductId(String productId);
}
