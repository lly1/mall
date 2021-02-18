package com.mall.api.dubbo.pc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.wx.order.TOrder;

import java.util.List;

/**
 * @author lly
 */
public interface DubboOrderService extends IService<TOrder> {
    TOrder findOrderById(String id);
    List<TOrder> findOrderByUserId0(String userId, Page<TOrder> page);
    List<TOrder> findOrderByUserId1(String userId, Page<TOrder> page);
    List<TOrder> findOrderByUserId2(String userId, Page<TOrder> page);
    List<TOrder> findOrderByShopId0(String shopId,Page<TOrder> page);
    List<TOrder> findOrderByShopId1(String shopId,Page<TOrder> page);
    List<TOrder> findOrder(Page<TOrder> page);
}
