package com.mall.service.component;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.component.TProductComponent;

import java.util.List;


public interface TProductComponentService extends IService<TProductComponent> {
    List<TProductComponent> findByProductId(String productId);
}
