package com.mall.wxshop.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.WxUserInfo;

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
     * @param wxUserInfo
     * 注册或登录
     */
    WxUserInfo loginOrRegisterConsumer(WxUserInfo wxUserInfo);
    /**
     * 获取当前请求的微信用户
     */
    WxUserInfo getCurrentWxUser();

}
