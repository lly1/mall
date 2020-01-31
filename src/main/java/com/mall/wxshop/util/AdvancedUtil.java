package com.mall.wxshop.util;



import com.alibaba.fastjson.JSONObject;
import com.mall.wxshop.entity.user.WeixinOauthToken;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.utils.CommonUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

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

    public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(
                decryptOfDiyIV(
                        Base64.decode(encryptDataB64),
                        Base64.decode(sessionKeyB64),
                        Base64.decode(ivB64)
                )
        );
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    private static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }
}
