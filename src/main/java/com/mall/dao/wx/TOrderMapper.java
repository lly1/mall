package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.wxshop.entity.order.TOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOrderMapper extends BaseMapper<TOrder> {
    TOrder findOrderById(String id);
    List<TOrder> findOrderByUserId0(@Param("userId") String userId,Page<TOrder> page);
    List<TOrder> findOrderByUserId1(@Param("userId") String userId,Page<TOrder> page);
    List<TOrder> findOrderByUserId2(@Param("userId") String userId,Page<TOrder> page);
    List<TOrder> findOrderByShopId0(@Param("shopId") String shopId,Page<TOrder> page);
    List<TOrder> findOrderByShopId1(@Param("shopId") String shopId,Page<TOrder> page);
    List<TOrder> findOrder(Page<TOrder> page);
}
