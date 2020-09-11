package com.mall.provider.service.wx.shop.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.provider.dao.wx.TCodeMapper;
import com.mall.provider.service.wx.shop.TCodeService;
import com.mall.api.entity.wx.shop.TCode;
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
