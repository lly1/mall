package com.mall.wxshop.service.user.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.constant.Constants;
import com.mall.dao.user.WxUserMapper;
import com.mall.service.user.UserService;
import com.mall.utils.UUIDGenerator;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.AppContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUserInfo> implements WxUserService {
    private static Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);

    @Resource
    private WxUserService wxUserService;
    @Resource
    RedisUtils redisUtils;

    @Override
    public void updateWxUserInfo(WxUserInfo wxUserInfo) {
        WxUserInfo currentWxUser = wxUserService.getCurrentWxUser();
        WxUserInfo wxUserInfoExist = this.getOne(new QueryWrapper<>(currentWxUser));
        wxUserInfo.setOpenId(wxUserInfoExist.getOpenId());
        wxUserInfo.setRoleId(wxUserInfoExist.getRoleId());
        wxUserInfo.setId(wxUserInfoExist.getId());
        this.saveOrUpdate(wxUserInfo);
    }

    @Override
    public WxUserInfo loginOrRegisterConsumer(WxUserInfo wxUserInfo) {
        WxUserInfo newWxUserInfo = this.getOne(new QueryWrapper<>(wxUserInfo));
        if (null == newWxUserInfo) {
            wxUserInfo.setId(UUIDGenerator.generate());
            wxUserInfo.setRoleId(Constants.Role.customer);
            boolean success = this.save(wxUserInfo);
            if(success){
                return wxUserInfo;
            }
        }
        return newWxUserInfo;
    }

    @Override
    public WxUserInfo getCurrentWxUser() {
        return getLoginUser();
    }

    private WxUserInfo getLoginUser() {
        WxUserInfo user = new WxUserInfo();
        try {
            Subject subject = SecurityUtils.getSubject();
            user = (WxUserInfo) subject.getPrincipal();
            if (user == null) {
                return null;
            }
            return user;
        } catch (Exception e) {
            logger.error("获取当前用户异常", e);
        }
        return user;
    }
}
