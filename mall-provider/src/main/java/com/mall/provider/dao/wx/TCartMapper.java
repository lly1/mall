package com.mall.provider.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.wx.sale.TCart;

import java.util.List;

public interface TCartMapper extends BaseMapper<TCart> {
    List<TCart> findCartById(TCart tCart);
    void delAllCart(TCart tCart);
}
