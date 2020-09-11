package com.mall.api.entity.wx.user;

import lombok.Data;

/**
 * 类名: WeixinOauth2Token </br>
 * 描述:  网页授权信息  </br>
 * 开发人员： lly </br>
 * 发布版本：V1.0  </br>
 */
@Data
public class WeixinOauthToken {
    // 网页授权接口调用凭证
    private String accessToken;
    // 凭证有效时长
    private String expiresIn;
    // 用于刷新凭证
    private String refreshToken;
    // 用户标识
    private String openId;
    // 用户授权作用域
    private String scope;
    //会话密钥
    private String sessionKey;

}