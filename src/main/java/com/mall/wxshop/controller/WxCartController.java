package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.user.User;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
import com.mall.wxshop.entity.sale.TCart;
import com.mall.wxshop.service.sale.TCartService;
import com.mall.wxshop.service.user.WxUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lly on 2019/12/2
 */
@Controller
@RequestMapping(value = "/api/cart")
public class WxCartController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private TCartService tCartService;

    @RequestMapping("/getCart")
    @ResponseBody
    public RtnMessage<List<TCart>> getCart(TCart tCart) {
        return RtnMessageUtils.buildSuccess(tCartService.findCartById(tCart));
    }

    @RequestMapping("saveCart")
    @ResponseBody
    public RtnMessage<TCart> saveShopProduct(TCart tCart){
        logAllRequestParams();
        if(StringUtilsEx.isBlank(tCart.getId())){
            tCart.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
        }else {
            tCart.preUpdate(new User(wxUserService.getCurrentWxUser().getNickName()));
        }
        tCartService.saveOrUpdate(tCart);
        return RtnMessageUtils.buildSuccess(tCartService.getById(tCart.getId()));
    }

    @RequestMapping("delCart")
    @ResponseBody
    public RtnMessage<TCart> delShopProduct(TCart tCart){
        if(tCart.getBuyNum() == 0){
            tCartService.removeById(tCart.getId());
        }else {
            tCartService.saveOrUpdate(tCart);
        }
        return RtnMessageUtils.buildSuccess(tCartService.getById(tCart.getId()));
    }

    @RequestMapping("delAllCart")
    @ResponseBody
    public RtnMessage<String> delAllCart(TCart tCart){
        tCartService.delAllCart(tCart);
        return RtnMessageUtils.buildSuccess("success");
    }

}
