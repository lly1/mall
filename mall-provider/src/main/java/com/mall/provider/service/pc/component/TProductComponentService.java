package com.mall.provider.service.pc.component;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.wx.component.TProductComponent;

import java.util.List;


public interface TProductComponentService extends IService<TProductComponent> {
    List<TProductComponent> findByProductId(String productId);
}
