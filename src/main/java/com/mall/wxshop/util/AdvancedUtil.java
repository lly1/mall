package com.mall.wxshop.util;



import com.alibaba.fastjson.JSONObject;
import com.mall.utils.HttpUtils;
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
        String result = HttpUtils.get(requestUrl, null);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (null != result) {
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
