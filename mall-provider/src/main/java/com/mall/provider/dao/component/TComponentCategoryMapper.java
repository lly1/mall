package com.mall.provider.dao.component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.component.TComponentCategory;

import java.util.List;


public interface TComponentCategoryMapper extends BaseMapper<TComponentCategory> {
    List<TComponentCategory> getComponent();
}
