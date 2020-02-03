package com.mall.wxshop.service.shop.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TShopCategoryMapper;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.shop.TShopCategory;
import com.mall.wxshop.service.shop.TShopCategoryService;
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
public class TShopProductServiceImpl extends ServiceImpl<TShopCategoryMapper, TShopCategory> implements TShopCategoryService {
    private static Logger logger = LoggerFactory.getLogger(TShopProductServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

}