package com.mall.provider.service.wx.order.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.provider.dao.wx.TOrderDetailMapper;
import com.mall.provider.service.wx.order.TOrderDetailService;
import com.mall.api.entity.wx.order.TOrderDetail;
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
public class TOrderDetailServiceImpl extends ServiceImpl<TOrderDetailMapper, TOrderDetail> implements TOrderDetailService {
    private static Logger logger = LoggerFactory.getLogger(TOrderDetailServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public List<TOrderDetail> findDetailByOrderId(String id) {
        return baseMapper.findDetailByOrderId(id);
    }

    @Override
    public List<TOrderDetail> findShopDetailByOrderId(String id) {
        return baseMapper.findShopDetailByOrderId(id);
    }
}
