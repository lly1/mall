package com.mall.wxshop.service.sale;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.sale.TCart;

import java.util.List;

/**
 * @author lly
 */
public interface TCartService extends IService<TCart> {
    List<TCart> findCartById(TCart tCart);
    void delAllCart(TCart tCart);
}
