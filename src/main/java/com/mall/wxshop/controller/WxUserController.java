package com.mall.wxshop.controller;

import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.utils.RtnMessageUtils;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lly on 2019/12/2
 */

@Controller
@RequestMapping(value = "/api/user")
public class WxUserController extends BaseController {

    @Resource
    private WxUserService wxUserService;

    @RequestMapping("/updateWxUserInfo")
    @ResponseBody
    public RtnMessage<WxUserInfo> updateConsumerInfo(@RequestBody WxUserInfo wxUserInfo) {
        wxUserInfo = wxUserService.updateWxUserInfo(wxUserInfo);
        return RtnMessageUtils.buildSuccess(wxUserInfo);
    }

}
