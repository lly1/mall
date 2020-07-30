package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.api.utils.RtnMessageUtils;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.service.shop.TShopService;
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
     * 根据店名模糊查询门店
     */
    @RequestMapping("getShopListByName")
    @ResponseBody
    public RtnMessage<List<TShop>> getShopListByName(TShop tShop){
        List<TShop> shopList = tShopService.list(new QueryWrapper<TShop>().like("shop_name",tShop.getShopName()));
        shopList.parallelStream().forEach(shop ->{
            shop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        });
        return RtnMessageUtils.buildSuccess(shopList);
    }
    /**
     * @param tShop
     * 默认顺序
     * @return
     */
    @RequestMapping("getShopList0")
    @ResponseBody
    public RtnMessage<Page<TShop>> getShopList(TShop tShop){
        Page<TShop> shopPage = tShopService.page(tShop.buildPage(),new QueryWrapper<TShop>().eq("shop_state","0"));
        List<TShop> shopList = shopPage.getRecords();
        shopList.parallelStream().forEach(shop ->{
            shop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        });
        shopPage.setRecords(shopList);
        return RtnMessageUtils.buildSuccess(shopPage);
    }
    /**
     * @param tShop
     * 销量排序
     * @return
     */
    @RequestMapping("getShopList1")
    @ResponseBody
    public RtnMessage<Page<TShop>> getShopList1(TShop tShop){
        Page<TShop> shopPage = tShopService.page(tShop.buildPage(),new QueryWrapper<TShop>().eq("shop_state","0"));
        List<TShop> shopList = shopPage.getRecords();
        shopList.parallelStream().forEach(shop ->{
            shop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        });
        shopList.sort((shop1, shop2) -> {
            Integer sale1 = shop1.getShopSale();
            Integer sale2 = shop2.getShopSale();
            return sale2.compareTo(sale1);
        });
        shopPage.setRecords(shopList);
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
        Page<TShop> shopPage = tShopService.page(tShop.buildPage(),new QueryWrapper<TShop>().eq("shop_state","0"));
        List<TShop> shopList = shopPage.getRecords();
        shopList.parallelStream().forEach(shop ->{
            shop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        });
        shopList.sort((shop1, shop2) -> {
            Double distance1 = Double.parseDouble(shop1.getDistance());
            Double distance2 = Double.parseDouble(shop2.getDistance());
            return distance1.compareTo(distance2);
        });
        shopPage.setRecords(shopList);
        return RtnMessageUtils.buildSuccess(shopPage);
    }
    /**
     * @param tShop
     * 根据评分
     * @return
     */
    @RequestMapping("getShopList3")
    @ResponseBody
    public RtnMessage<Page<TShop>> getShopList3(TShop tShop){
        Page<TShop> shopPage = tShopService.page(tShop.buildPage(),new QueryWrapper<TShop>().eq("shop_state","0"));
        List<TShop> shopList = shopPage.getRecords();
        shopList.parallelStream().forEach(shop ->{
            shop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        });
        shopList.sort((shop1, shop2) -> {
            Double star1 = shop1.getShopStar();
            Double star2 = shop2.getShopStar();
            return star2.compareTo(star1);
        });
        shopPage.setRecords(shopList);
        return RtnMessageUtils.buildSuccess(shopPage);
    }
}
