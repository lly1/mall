package com.mall.provider.service.wx.user.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.constant.Constants;
import com.mall.dao.user.WxUserMapper;
import com.mall.api.entity.user.User;
import com.mall.api.entity.user.UserRole;
import com.mall.provider.service.wx.user.WxUserService;
import com.mall.service.role.UserRoleService;
import com.mall.api.utils.cache.RedisUtils;
import com.mall.api.utils.cache.SpringContextUtil;
import com.mall.wx.entity.user.WxUserInfo;
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
@Service("WxUserService")
@Transactional(rollbackFor = Exception.class)
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUserInfo> implements WxUserService {
    private static Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private WxUserService wxUserService;
    @Resource
    RedisUtils redisUtils;

    @Override
    public WxUserInfo updateWxUserInfo(WxUserInfo wxUserInfo) {
        WxUserInfo currentWxUser = this.getCurrentWxUser();
        SpringContextUtil.copyPropertiesIgnoreNull(wxUserInfo,currentWxUser);
        currentWxUser.preUpdate(new User("admin"));
        this.saveOrUpdate(currentWxUser);
        return currentWxUser;
    }

    @Override
    public WxUserInfo businessRegister(String id, String phone) {
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRole.setRoleId(Constants.Role.BUSINESS_SHOP);
        userRole.preInsert(new User("admin"));
        boolean success = userRoleService.save(userRole);
        if(success){
            WxUserInfo wxUserInfo = wxUserService.getCurrentWxUser();
            wxUserInfo.setPhone(phone);
            wxUserService.saveOrUpdate(wxUserInfo);
            return wxUserInfo;
        }
        return null;
    }

    @Override
    public WxUserInfo loginOrRegisterCustomer(WxUserInfo wxUserInfo) {
        WxUserInfo newWxUserInfo = this.getOne(new QueryWrapper<>(wxUserInfo));
        if (null == newWxUserInfo) {
            wxUserInfo.preInsert(new User("admin"));
            UserRole userRole = new UserRole();
            userRole.setUserId(wxUserInfo.getId());
            userRole.setRoleId(Constants.Role.CUSTOMER);
            userRole.preInsert(new User("admin"));
            boolean success = wxUserService.save(wxUserInfo);
            boolean success1 = userRoleService.save(userRole);
            if(success && success1){
                return wxUserInfo;
            }
        }
        return newWxUserInfo;
    }

    @Override
    public WxUserInfo getCurrentWxUser() {
        return getLoginUser();
    }

    @Override
    public WxUserInfo getWxUser(String id) {
        return baseMapper.getWxUser(id);
    }

    private WxUserInfo getLoginUser() {
        WxUserInfo user = new WxUserInfo();
        try {
            Subject subject = SecurityUtils.getSubject();
            user = (WxUserInfo) subject.getPrincipal();
            if (user == null) {
                return null;
            }
            user =  wxUserService.getWxUser(user.getId());
            List<UserRole> roles = user.getRoles();
            if(roles.size() > 1){
                List<String> roleIds = new LinkedList<>();
                //获取所有的角色，设置最大的角色为主要角色返回
                roles.parallelStream().forEach(item ->{
                    roleIds.add(item.getRoleId());
                });
                user.setRoleId(Collections.min(roleIds));
            }else {
                user.setRoleId(roles.get(0).getRoleId());
            }
            return user;
        } catch (Exception e) {
            logger.error("获取当前用户异常", e);
        }
        return user;
    }
}
