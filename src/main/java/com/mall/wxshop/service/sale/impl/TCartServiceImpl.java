package com.mall.wxshop.service.sale.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TCartMapper;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.sale.TCart;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.service.sale.TCartService;
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
public class TCartServiceImpl extends ServiceImpl<TCartMapper, TCart> implements TCartService {
    private static Logger logger = LoggerFactory.getLogger(TCartServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public List<TCart> findCartById(TCart tCart) {
        return baseMapper.findCartById(tCart);
    }

    @Override
    public void delAllCart(TCart tCart) {
        baseMapper.delAllCart(tCart);
    }
}
