package com.mall.wxshop.service.order.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TOrderMapper;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.order.TOrder;
import com.mall.wxshop.entity.sale.TCart;
import com.mall.wxshop.service.order.TOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信api实现类
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    private static Logger logger = LoggerFactory.getLogger(TOrderServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public TOrder findOrderById(String id) {
        return baseMapper.findOrderById(id);
    }

    @Override
    public List<TOrder> findOrderByUserId0(String userId, Page<TOrder> page) {
        return baseMapper.findOrderByUserId0(userId,page);
    }
    @Override
    public List<TOrder> findOrderByUserId1(String userId, Page<TOrder> page) {
        return baseMapper.findOrderByUserId1(userId,page);
    }
    @Override
    public List<TOrder> findOrderByUserId2(String userId, Page<TOrder> page) {
        return baseMapper.findOrderByUserId2(userId,page);
    }

    @Override
    public List<TOrder> findOrderByShopId0(String shopId, Page<TOrder> page) {
        return baseMapper.findOrderByShopId0(shopId,page);
    }

    @Override
    public List<TOrder> findOrderByShopId1(String shopId, Page<TOrder> page) {
        return baseMapper.findOrderByShopId1(shopId,page);
    }
}
