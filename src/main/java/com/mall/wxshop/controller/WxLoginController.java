package com.mall.wxshop.controller;

import com.mall.common.RtnMessage;
import com.mall.constant.ErrorType;
import com.mall.filter.WxAuthenticationFilter;
import com.mall.service.user.UserService;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.config.WxConfig;
import com.mall.wxshop.entity.WeixinOauthToken;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.AdvancedUtil;
import com.mall.common.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.ref.PhantomReference;

/**
 * Created by lly on 2019/12/2
 */

@Controller
@RequestMapping(value = "/wxAuth")
public class WxLoginController extends BaseController {

    @Resource
    private WxAuthenticationFilter wxAuthenticationFilter;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private RedisUtils redisUtils;

    @RequestMapping("login")
    @ResponseBody
    public RtnMessage<WeixinOauthToken> wxLogin(HttpServletRequest request, HttpServletResponse response){
        String js_code = request.getParameter("js_code");
        WeixinOauthToken weixinOauthToken  =  AdvancedUtil.wxLogin(WxConfig.APP_ID,WxConfig.APP_SECRET,js_code);
        // 用户标识
        String openId = weixinOauthToken.getOpenId();
        request.setAttribute("openId",openId);
        try {
            wxAuthenticationFilter.executeLogin(request,response);
            Serializable sid = SecurityUtils.getSubject().getSession().getId();
            //用sessionid当token，以后每次请求都带sessionid
            weixinOauthToken.setAccessToken(sid.toString());
        }catch (Exception e){
            return RtnMessageUtils.buildFailed(null);
        }
        return RtnMessageUtils.buildSuccess(weixinOauthToken);
    }

}
