package com.mall.provider.service.wx.sale.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.api.entity.wx.sale.TComment;
import com.mall.provider.dao.wx.TCommentMapper;
import com.mall.provider.service.wx.sale.TCommentService;
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
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment> implements TCommentService {
    private static Logger logger = LoggerFactory.getLogger(TCommentServiceImpl.class);

    @Resource
    RedisUtils redisUtils;

    @Override
    public Page<TComment> findCommentByShopId(String shopId, Page<TComment> page) {
        return baseMapper.findCommentByShopId(shopId,page);
    }
}
