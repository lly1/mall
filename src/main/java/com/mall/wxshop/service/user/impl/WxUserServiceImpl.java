package com.mall.wxshop.service.user.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.constant.Constants;
import com.mall.dao.user.WxUserMapper;
import com.mall.utils.UUIDGenerator;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.AppContext;
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
    @Resource
    RedisUtils redisUtils;

    @Override
    public void updateWxUserInfo(WxUserInfo wxUserInfo) {
        WxUserInfo wxUserInfoExist = this.getOne(new QueryWrapper<WxUserInfo>().eq("openId", AppContext.getCurrentUserWechatOpenId()));
        wxUserInfo.setOpenId(wxUserInfoExist.getOpenId());
        wxUserInfo.setRoleId(wxUserInfoExist.getRoleId());
        wxUserInfo.setId(wxUserInfoExist.getId());
        this.saveOrUpdate(wxUserInfo);
    }

    @Override
    public String loginOrRegisterConsumer(WxUserInfo wxUserInfo) {
        WxUserInfo newWxUserInfo = this.getOne(new QueryWrapper<WxUserInfo>().eq("openId",wxUserInfo.getOpenId()));
        if (null == newWxUserInfo) {
            wxUserInfo.setId(UUIDGenerator.generate());
            wxUserInfo.setRoleId(Constants.Role.customer);
            boolean success = this.save(wxUserInfo);
            if(success){
                return wxUserInfo.getId();
            }
        }
        return newWxUserInfo.getId();
    }

    @Override
    public WxUserInfo getCurrentWxUser() {
        return this.getOne(new QueryWrapper<WxUserInfo>().eq("openId", AppContext.getCurrentUserWechatOpenId()));
    }
}
