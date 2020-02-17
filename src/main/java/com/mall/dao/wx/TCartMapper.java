package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.sale.TCart;

import java.util.List;

public interface TCartMapper extends BaseMapper<TCart> {
    List<TCart> findCartById(TCart tCart);
    void delAllCart(TCart tCart);
}
