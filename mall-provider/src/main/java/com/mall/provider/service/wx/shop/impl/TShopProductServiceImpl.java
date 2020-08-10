package com.mall.provider.service.wx.shop.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TShopCategoryMapper;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.provider.service.wx.shop.TShopCategoryService;
import com.mall.wx.entity.shop.TShopCategory;
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
public class TShopProductServiceImpl extends ServiceImpl<TShopCategoryMapper, TShopCategory> implements TShopCategoryService {
    private static Logger logger = LoggerFactory.getLogger(TShopProductServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public List<TShopCategory> getShopCategory(String shopId) {
        return baseMapper.getShopCategory(shopId);
    }

    @Override
    public List<TShopCategory> getShopCategorySale(String shopId) {
        return baseMapper.getShopCategorySale(shopId);
    }
}
