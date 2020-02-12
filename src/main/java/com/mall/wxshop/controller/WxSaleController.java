package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.utils.RtnMessageUtils;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.service.shop.TShopService;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.DistanceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lly on 2019/12/2
 * 不需要登录可以访问的Controller
 */
@Controller
@RequestMapping(value = "/sale/shop")
public class WxSaleController extends BaseController {

    @Resource
    private TShopService tShopService;

    /**
     * @param tShop
     * 默认顺序
     * @return
     */
    @RequestMapping("getShopList0")
    @ResponseBody
    public RtnMessage<Page<TShop>> getShopList(TShop tShop){
        Page<TShop> shopPage = tShopService.page(tShop.buildPage());
        return RtnMessageUtils.buildSuccess(shopPage);
    }

    /**
     * @param tShop
     * 根据距离
     * @return
     */
    @RequestMapping("getShopList2")
    @ResponseBody
    public RtnMessage<Page<TShop>> getShopByDistance(TShop tShop){
        Page<TShop> shopPage = tShopService.page(tShop.buildPage());
        List<TShop> shopList = shopPage.getRecords();
        shopList.forEach(shop ->{
            shop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        });
        shopList.sort((shop1, shop2) -> {
            String distance1 = shop1.getDistance();
            String distance2 = shop2.getDistance();
            return distance1.compareTo(distance2);
        });
        shopPage.setRecords(shopList);
        return RtnMessageUtils.buildSuccess(shopPage);
    }

}
