package com.mall.provider.service.wx.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.wx.order.TOrderDetail;

import java.util.List;

/**
 * @author lly
 */
public interface TOrderDetailService extends IService<TOrderDetail> {
    List<TOrderDetail> findDetailByOrderId(String id);
    List<TOrderDetail> findShopDetailByOrderId(String id);
}
