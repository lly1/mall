package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.user.User;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.wxshop.service.shop.TShopService;
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
@RequestMapping(value = "/api/shop")
public class WxShopController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private TShopService tShopService;


    /**
     * 店铺基本信息
     * */
    @RequestMapping("getShopInfo")
    @ResponseBody
    public RtnMessage<TShop> getShopInfo(String userId){
        TShop tShop = tShopService.getOne(new QueryWrapper<TShop>().eq("user_id",userId));
        return RtnMessageUtils.buildSuccess(tShop);
    }
    /**
     * 店铺基本信息
     * */
    @RequestMapping("saveShopInfo")
    @ResponseBody
    public RtnMessage<String> saveShopInfo(@RequestBody TShop tShop){
        logAllRequestParams();
        if(StringUtilsEx.isBlank(tShop.getId())){
            tShop.preInsert(new User("admin"));
            //默认闭店，信息填写完成开店
            tShop.setShopState("0");
            //默认满分
            tShop.setShopStar(5.0);
        }else {
            tShop.preUpdate(new User("admin"));
        }
        tShopService.saveOrUpdate(tShop);
        return RtnMessageUtils.buildSuccess("success");
    }

}
