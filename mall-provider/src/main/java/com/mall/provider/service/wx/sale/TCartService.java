package com.mall.provider.service.wx.sale;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.wx.sale.TCart;

import java.util.List;

/**
 * @author lly
 */
public interface TCartService extends IService<TCart> {
    List<TCart> findCartById(TCart tCart);
    void delAllCart(TCart tCart);
}
