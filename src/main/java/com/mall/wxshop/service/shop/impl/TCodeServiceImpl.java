package com.mall.wxshop.service.shop.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TCodeMapper;
import com.mall.dao.wx.TShopMapper;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.shop.TCode;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.service.shop.TCodeService;
import com.mall.wxshop.service.shop.TShopService;
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
public class TCodeServiceImpl extends ServiceImpl<TCodeMapper, TCode> implements TCodeService {
    private static Logger logger = LoggerFactory.getLogger(TCodeServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public Integer getCodeByShopId(String shopId) {
        return baseMapper.getCodeByShopId(shopId);
    }

    @Override
    public int updateCode(Integer code,String shopId) {
        return baseMapper.updateCode(code,shopId);
    }
}
