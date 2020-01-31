package com.mall.wxshop.service.shop.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.constant.Constants;
import com.mall.dao.wx.TShopMapper;
import com.mall.entity.user.User;
import com.mall.entity.user.UserRole;
import com.mall.service.role.UserRoleService;
import com.mall.utils.cache.RedisUtils;
import com.mall.utils.cache.SpringContextUtil;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.wxshop.service.shop.TShopService;
import com.mall.wxshop.service.user.WxUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

}
