package com.mall.provider.service.pc.component;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.component.TComponentCategory;
import com.mall.api.entity.component.TProductComponent;

import java.util.List;


public interface TComponentCategoryService extends IService<TComponentCategory> {
    List<TComponentCategory> getComponent();
}
