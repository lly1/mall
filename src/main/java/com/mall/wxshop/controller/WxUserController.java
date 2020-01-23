package com.mall.wxshop.controller;

import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.filter.WxAuthenticationFilter;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.config.WxConfig;
import com.mall.wxshop.entity.WeixinOauthToken;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.AdvancedUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value = "/api/user")
public class WxUserController extends BaseController {

    @Resource
    private WxUserService wxUserService;

    @RequestMapping("/updateWxUserInfo")
    public void updateConsumerInfo(@RequestBody WxUserInfo wxUserInfo) {
        wxUserService.updateWxUserInfo(wxUserInfo);
    }

}
