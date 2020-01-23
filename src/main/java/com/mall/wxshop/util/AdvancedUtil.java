package com.mall.wxshop.util;



import com.alibaba.fastjson.JSONObject;
import com.mall.wxshop.entity.WeixinOauthToken;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.constant.Constants;
import com.mall.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lly on 2019/12/2
 */
public class AdvancedUtil {

    public static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
    /**
     * 获取网页授权凭证
     *
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @return WeixinAouthToken
     */
    public static WeixinOauthToken getOauth2AccessToken(String appId, String appSecret, String js_code) {
        WeixinOauthToken wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("JSCODE", js_code);
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauthToken();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getString("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return WxUserInfo
     */
    public static WxUserInfo getWxUserInfo(String accessToken, String openId) {
        WxUserInfo wxUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                wxUserInfo = new WxUserInfo();
                // 用户的标识
                wxUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                wxUserInfo.setNickName(jsonObject.getString("nikeName"));
                // 性别（1是男性，2是女性，0是未知）
                wxUserInfo.setGender(jsonObject.getString("sex"));
                // 用户所在国家
                wxUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                wxUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                wxUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                wxUserInfo.setAvatarUrl(jsonObject.getString("avatarUrl"));
                //用户唯一码
                wxUserInfo.setUnionId(jsonObject.getString("unionid"));
                // 用户特权信息
                wxUserInfo.setRoleId(Constants.Role.customer);
            } catch (Exception e) {
                wxUserInfo = null;
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wxUserInfo;
    }

    /**
     * 微信登录
     */
    public static WeixinOauthToken wxLogin(String appId, String appSecret, String js_code) {
        WeixinOauthToken wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("JSCODE", js_code);
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauthToken();
                wat.setSessionKey(jsonObject.getString("session_key"));
                wat.setOpenId(jsonObject.getString("openid"));
            } catch (Exception e) {
                wat = null;
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("登录失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
}
