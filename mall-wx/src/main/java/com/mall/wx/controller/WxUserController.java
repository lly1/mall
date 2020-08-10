package com.mall.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.api.utils.RtnMessageUtils;
import com.mall.wx.entity.order.TOrder;
import com.mall.wx.entity.user.WxUserInfo;
import com.mall.provider.service.wx.service.order.TOrderService;
import com.mall.provider.service.wx.service.shop.TShopService;
import com.mall.provider.service.wx.service.user.WxUserService;
import com.mall.wx.util.AdvancedUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lly on 2019/12/2
 */
@Controller
@RequestMapping(value = "/api/user")
public class WxUserController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private TShopService tShopService;
    @Resource
    private TOrderService tOrderService;

    @RequestMapping("/updateWxUserInfo")
    @ResponseBody
    public RtnMessage<WxUserInfo> updateConsumerInfo(@RequestBody WxUserInfo wxUserInfo) {
        wxUserInfo = wxUserService.updateWxUserInfo(wxUserInfo);
        wxUserInfo.setShop(tShopService.getShop(wxUserInfo.getId()));
        //查一下店里的订单
        if("1".equals(wxUserInfo.getRoleId())){
            List<TOrder> orderList = tOrderService.list(new QueryWrapper<TOrder>()
                    .eq("shop_id",wxUserInfo.getShop().getId())
                    .eq("order_status",1));
            wxUserInfo.setNewOrder(orderList.size());
        }
        return RtnMessageUtils.buildSuccess(wxUserInfo);
    }

    @RequestMapping("/businessRegister")
    @ResponseBody
    public RtnMessage businessRegister(String id,String phone) {
        WxUserInfo wxUserInfo = null;
        try {
            wxUserInfo = wxUserService.businessRegister(id,phone);
        }catch (Exception e){
            RtnMessageUtils.buildFailed("手机号已注册");
        }
        return RtnMessageUtils.buildSuccess(wxUserInfo);
    }

    @RequestMapping("/setPass")
    @ResponseBody
    public RtnMessage<WxUserInfo> setPass(String id,String pass) {
        WxUserInfo wxUserInfo = wxUserService.getById(id);
        wxUserInfo.setPayPass(pass);
        wxUserService.saveOrUpdate(wxUserInfo);
        return RtnMessageUtils.buildSuccess(wxUserInfo);
    }

    @RequestMapping("/decodePhone")
    @ResponseBody
    public RtnMessage<String> decodePhone(String encryptedData ,String sessionKey,String iv) {
        String result = AdvancedUtil.decryptData(encryptedData,sessionKey,iv);
        return RtnMessageUtils.buildSuccess(result);
    }

}
