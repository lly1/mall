package com.mall.wxshop.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.order.TOrder;
import com.mall.wxshop.entity.order.TOrderDetail;

import java.util.List;

/**
 * @author lly
 */
public interface TOrderDetailService extends IService<TOrderDetail> {
    List<TOrderDetail> findDetailByOrderId(String id);
    List<TOrderDetail> findShopDetailByOrderId(String id);
}
