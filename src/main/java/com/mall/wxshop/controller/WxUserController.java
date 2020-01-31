package com.mall.wxshop.controller;

import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.utils.RtnMessageUtils;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.AdvancedUtil;
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

    @RequestMapping("/businessRegister")
    @ResponseBody
    public RtnMessage<WxUserInfo> businessRegister(String id,String phone) {
        WxUserInfo wxUserInfo = wxUserService.businessRegister(id,phone);
        return RtnMessageUtils.buildSuccess(wxUserInfo);
    }

    @RequestMapping("/decodePhone")
    @ResponseBody
    public RtnMessage<String> decodePhone(String encryptedData ,String sessionKey,String iv) {
        String result = AdvancedUtil.decryptData(encryptedData,sessionKey,iv);
        return RtnMessageUtils.buildSuccess(result);
    }

}
