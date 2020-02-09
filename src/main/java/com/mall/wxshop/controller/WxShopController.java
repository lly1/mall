package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.user.User;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.entity.shop.TShopCategory;
import com.mall.wxshop.service.shop.TShopCategoryService;
import com.mall.wxshop.service.shop.TShopService;
import com.mall.wxshop.service.user.WxUserService;
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
@RequestMapping(value = "/api/shop")
public class WxShopController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private TShopService tShopService;
    @Resource
    private TShopCategoryService tShopCategoryService;


    /**
     * 店铺基本信息
     * */
    @RequestMapping("getShopInfo")
    @ResponseBody
    public RtnMessage<TShop> getShopInfo(String userId){
        TShop tShop = tShopService.getShop(userId);
        return RtnMessageUtils.buildSuccess(tShop);
    }
    /**
     * 店铺基本信息
     * */
    @RequestMapping("saveShopInfo")
    @ResponseBody
    public RtnMessage<TShop> saveShopInfo(@RequestBody TShop tShop){
        logAllRequestParams();
        if(StringUtilsEx.isBlank(tShop.getId())){
            tShop.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
            //默认闭店，信息填写完成开店
            tShop.setShopState("0");
            //默认满分
            tShop.setShopStar(5.0);
        }else {
            tShop.preUpdate(new User(wxUserService.getCurrentWxUser().getNickName()));
        }
        tShopService.saveOrUpdate(tShop);
        return RtnMessageUtils.buildSuccess(tShop);
    }
    /**
     * 店铺类目信息
     * */
    @RequestMapping("getShopCategory")
    @ResponseBody
    public RtnMessage<List<TShopCategory>> getShopCategory(String shopId){
        List<TShopCategory> categoryList = tShopCategoryService.list(new QueryWrapper<TShopCategory>().eq("shop_id",shopId));
        return RtnMessageUtils.buildSuccess(categoryList);
    }
    /**
     * 店铺类目信息
     * */
    @RequestMapping("saveShopCategory")
    @ResponseBody
    public RtnMessage<List<TShopCategory>> saveShopCategory(TShopCategory tShopCategory){
        logAllRequestParams();
        if(StringUtilsEx.isBlank(tShopCategory.getId())){
            tShopCategory.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
        }else {
            tShopCategory.preUpdate(new User(wxUserService.getCurrentWxUser().getNickName()));
        }
        tShopCategoryService.saveOrUpdate(tShopCategory);
        return RtnMessageUtils.buildSuccess(tShopCategoryService.list());
    }
    @RequestMapping("delShopCategory")
    @ResponseBody
    public RtnMessage<List<TShopCategory>> delShopCategory(String id){
        tShopCategoryService.removeById(id);
        return RtnMessageUtils.buildSuccess(tShopCategoryService.list());
    }

}
