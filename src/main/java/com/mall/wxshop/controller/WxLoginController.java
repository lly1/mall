package com.mall.wxshop.controller;

import com.mall.common.RtnMessage;
import com.mall.constant.ErrorType;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.config.WxConfig;
import com.mall.wxshop.entity.WeixinOauthToken;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.AdvancedUtil;
import com.mall.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lly on 2019/12/2
 */

@Controller
@RequestMapping(value = "/api/wx/auth")
public class WxLoginController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 微信授权后回调请求
     * */
    @RequestMapping("getOauthInfo")
    public void getOauthInfo(HttpServletRequest request){
        this.logAllRequestParams();
        // 用户同意授权后，能获取到code
        String js_code = request.getParameter("js_code");
        // 用户同意授权
        if(!WxConfig.AUTHDENY.equals(js_code)){
            WeixinOauthToken weixinOauthToken = AdvancedUtil.getOauth2AccessToken(WxConfig.APP_ID,WxConfig.APP_SECRET,js_code);
            String accessToken = weixinOauthToken.getAccessToken();
            // 用户标识
            String openId = weixinOauthToken.getOpenId();
            // 获取用户信息
            WxUserInfo wxUserInfo = AdvancedUtil.getWxUserInfo(accessToken, openId);
            this.wxUserService.save(wxUserInfo);
        }
    }
    @RequestMapping("login")
    @ResponseBody
    public RtnMessage<String> wxLogin(HttpServletRequest request){
        String js_code = request.getParameter("js_code");
        WeixinOauthToken weixinOauthToken  =  AdvancedUtil.wxLogin(WxConfig.APP_ID,WxConfig.APP_SECRET,js_code);
        WxUserInfo wxUserInfo = new WxUserInfo();
        String sessionKey = weixinOauthToken.getSessionKey();
        // 用户标识
        String openId = weixinOauthToken.getOpenId();
        wxUserInfo.setOpenId(openId);
        //查询用户，不存在就插入
        String userId = wxUserService.loginOrRegisterConsumer(wxUserInfo);
        //用userId做token
        String token = createToken(userId,openId,sessionKey,WxConfig.EXPIRES);
        weixinOauthToken.setAccessToken(token);
        return RtnMessageUtils.buildResult(ErrorType.SUCCESS.getErrorCode(),"登录成功");
    }
    @RequestMapping("/updateWxUserInfo")
    public void updateConsumerInfo(@RequestBody WxUserInfo wxUserInfo) {
        wxUserService.updateWxUserInfo(wxUserInfo);
    }

    private String createToken(String userId,String wxOpenId, String wxSessionKey, Long expires) {
        StringBuffer sb = new StringBuffer();
        sb.append(wxSessionKey).append("#").append(wxOpenId);
        //保存到redis
        redisUtils.set(userId, sb.toString(), expires);
        return userId;
    }
}
