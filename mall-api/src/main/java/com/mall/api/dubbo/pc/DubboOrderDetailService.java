package com.mall.api.dubbo.pc;

import com.mall.api.entity.pc.user.User;
import com.mall.api.entity.wx.order.TOrderDetail;

import java.util.List;

/**
 * @author lly
 */
public interface DubboOrderDetailService {
    List<TOrderDetail> findDetailByOrderId(String id);
    List<TOrderDetail> findShopDetailByOrderId(String id);
}
