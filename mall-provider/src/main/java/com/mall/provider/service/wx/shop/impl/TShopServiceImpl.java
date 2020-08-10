package com.mall.provider.service.wx.shop.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TShopMapper;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.provider.service.wx.shop.TShopService;
import com.mall.wx.entity.shop.TShop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 微信api实现类
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TShopServiceImpl extends ServiceImpl<TShopMapper, TShop> implements TShopService {
    private static Logger logger = LoggerFactory.getLogger(TShopServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public TShop getShop(String userId) {
        return baseMapper.getShop(userId);
    }
}
