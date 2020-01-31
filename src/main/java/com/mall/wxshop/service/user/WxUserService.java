package com.mall.wxshop.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.user.WxUserInfo;

/**
 * @author lly
 * 微信用户操作service
 */
public interface WxUserService extends IService<WxUserInfo> {
    /**
     * @param wxUserInfo
     * 更新用户信息
     */
    WxUserInfo updateWxUserInfo(WxUserInfo wxUserInfo);
    /**
     * 注册成为商家
     */
    WxUserInfo businessRegister(String id,String phone);

    /**
     * @param wxUserInfo
     * 注册或登录
     */
    WxUserInfo loginOrRegisterCustomer(WxUserInfo wxUserInfo);
    /**
     * 获取当前请求的微信用户
     */
    WxUserInfo getCurrentWxUser();

    /**
     * @param id 获取带权限的WxUser
     * @return
     */
    WxUserInfo getWxUser(String id);

}
