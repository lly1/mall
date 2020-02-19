package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.order.TOrderDetail;

import java.util.List;

/**
 * @author lly
 */
public interface TOrderDetailMapper extends BaseMapper<TOrderDetail> {
    List<TOrderDetail> findDetailByOrderId(String id);
    List<TOrderDetail> findShopDetailByOrderId(String id);
}
