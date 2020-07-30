package com.mall.wxshop.service.shop.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.wx.TShopProductMapper;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.wxshop.entity.shop.TShopProduct;
import com.mall.wxshop.service.shop.TShopProductService;
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
public class TShopCategoryServiceImpl extends ServiceImpl<TShopProductMapper, TShopProduct> implements TShopProductService {
    private static Logger logger = LoggerFactory.getLogger(TShopCategoryServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public List<TShopProduct> getProductUserCart(String userId, String categoryId) {
        return baseMapper.getProductUserCart(userId,categoryId);
    }
}
