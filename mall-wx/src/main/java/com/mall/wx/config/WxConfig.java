package com.mall.wx.config;

/**
 * Created by lly on 2019/12/2.
 * **
 * 微信公众号相关的配置
 * **
 */
public class WxConfig {

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    public static final Long EXPIRES = 86400L;

    public static final String header = "Authorization";

    public static final String tokenHead = "mall";

    public static final String APP_ID = "wx4d256cf6c5b4ecd0";

    public static final String APP_SECRET = "c4904a87f6bbc23c9b069c99495ca230";
    // 授权链接
    public static final String OAUTH_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    // 获取token的链接
    public static final String OAUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    // 刷新token
    public static final String OAUTH_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    // 获取授权用户信息
    public static final String SNS_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
    // 判断用户accessToken是否有效
    public static final String SNS_AUTH_URL = "https://api.weixin.qq.com/sns/auth";

    public static final String AUTHDENY="authdeny";
}
