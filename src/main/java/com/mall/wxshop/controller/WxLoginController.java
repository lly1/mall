package com.mall.wxshop.controller;

import com.mall.common.RtnMessage;
import com.mall.filter.WxAuthenticationFilter;
import com.mall.api.utils.RtnMessageUtils;
import com.mall.wxshop.config.WxConfig;
import com.mall.wxshop.entity.user.WeixinOauthToken;
import com.mall.wxshop.util.AdvancedUtil;
import com.mall.common.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by lly on 2019/12/2
 */

@Controller
@RequestMapping(value = "/wxAuth")
public class WxLoginController extends BaseController {

    @Resource
    private WxAuthenticationFilter wxAuthenticationFilter;

    @RequestMapping("login")
    @ResponseBody
    public RtnMessage<WeixinOauthToken> wxLogin(HttpServletRequest request, HttpServletResponse response){
        String js_code = request.getParameter("js_code");
        WeixinOauthToken weixinOauthToken = AdvancedUtil.wxLogin(WxConfig.APP_ID,WxConfig.APP_SECRET,js_code);
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
