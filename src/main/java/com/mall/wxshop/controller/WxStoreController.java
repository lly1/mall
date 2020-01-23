package com.mall.wxshop.controller;

import com.mall.common.BaseController;
import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lly on 2019/12/2
 */

@Controller
@RequestMapping(value = "/api/wx/store")
public class WxStoreController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 商家所需信息填写
     * */
    @RequestMapping("becomeBusiness")
    public void becomeBusiness(HttpServletRequest request){
        logAllRequestParams();
        WxUserInfo currentWxUser = wxUserService.getCurrentWxUser();
        System.out.println(currentWxUser.getNickName());
    }

}
