package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.component.TProductComponent;
import com.mall.entity.user.User;
import com.mall.service.component.TProductComponentService;
import com.mall.utils.CommonUtil;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
import com.mall.utils.UUIDGenerator;
import com.mall.wxshop.entity.sale.TComment;
import com.mall.wxshop.entity.shop.TCode;
import com.mall.wxshop.entity.shop.TShop;
import com.mall.wxshop.entity.shop.TShopCategory;
import com.mall.wxshop.entity.shop.TShopProduct;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.wxshop.service.sale.TCommentService;
import com.mall.wxshop.service.shop.TCodeService;
import com.mall.wxshop.service.shop.TShopCategoryService;
import com.mall.wxshop.service.shop.TShopProductService;
import com.mall.wxshop.service.shop.TShopService;
import com.mall.wxshop.service.user.WxUserService;
import com.mall.wxshop.util.DistanceUtil;
import org.apache.commons.collections.CollectionUtils;
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
    @Resource
    private TShopProductService tShopProductService;
    @Resource
    private TCodeService tCodeService;
    @Resource
    private TCommentService tCommentService;
    @Resource
    private TProductComponentService tProductComponentService;


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
     * 店铺评价信息
     * */
    @RequestMapping("getShopComment")
    @ResponseBody
    public RtnMessage<Page<TComment>> getShopComment(TComment tComment){
        Page<TComment> comments = tCommentService.findCommentByShopId(tComment.getShopId(),tComment.buildPage());
        return RtnMessageUtils.buildSuccess(comments);
    }
    /**
     * 店铺基本信息
     * */
    @RequestMapping("getShopInfoById")
    @ResponseBody
    public RtnMessage<TShop> getShopInfoById(TShop shop){
        TShop tShop = tShopService.getById(shop.getId());
        //商品详情需要位置
        if(CommonUtil.isNotBlank(shop.getLatitude())) {
            tShop.setDistance(DistanceUtil.getDistance(Double.parseDouble(tShop.getLatitude()),
                    Double.parseDouble(tShop.getLongitude()),
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())));
        }
        return RtnMessageUtils.buildSuccess(tShop);
    }
    /**
     * 店铺基本信息
     * */
    @RequestMapping("saveShopInfo")
    @ResponseBody
    public RtnMessage<TShop> saveShopInfo(@RequestBody TShop tShop){
        logAllRequestParams();
        WxUserInfo wxUserInfo = wxUserService.getCurrentWxUser();
        if(StringUtilsEx.isBlank(tShop.getId())){
            tShop.preInsert(new User(wxUserInfo.getNickName()));
            //默认闭店，信息填写完成开店
            tShop.setShopState("0");
            //默认满分
            tShop.setShopStar(5.0);
            tShop.setPhone(wxUserInfo.getPhone());
            //插入取货码表
            TCode code = new TCode();
            code.preInsert(new User(wxUserInfo.getNickName()));
            code.setCode(1);
            code.setShopId(tShop.getId());
            tCodeService.save(code);
        }else {
            tShop.preUpdate(new User(wxUserInfo.getNickName()));
        }
        tShopService.saveOrUpdate(tShop);
        return RtnMessageUtils.buildSuccess(tShopService.getShop(tShop.getUserId()));
    }
    /**
     * 店铺类目商品信息
     * */
    @RequestMapping("getShopCategory")
    @ResponseBody
    public RtnMessage<List<TShopCategory>> getShopCategory(String shopId,String isSale,String userId){
        List<TShopCategory> categoryList = null;
        //非售卖页面显示所有商品
        if("0".equals(isSale)){
            categoryList = tShopCategoryService.getShopCategory(shopId);
        }else {
            categoryList = tShopCategoryService.getShopCategorySale(shopId);
            //查询此人在店铺里的商品有没有购物车
            if(CommonUtil.isNotBlank(userId) && CollectionUtils.isNotEmpty(categoryList)){
                categoryList.parallelStream().forEach(tShopCategory -> {
                    tShopCategory.setShopProducts(tShopProductService.getProductUserCart(userId,tShopCategory.getId()));
                });
            }
        }
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
        return RtnMessageUtils.buildSuccess(tShopCategoryService.list(new QueryWrapper<TShopCategory>().eq("shop_id",tShopCategory.getShopId())));
    }
    @RequestMapping("delShopCategory")
    @ResponseBody
    public RtnMessage<List<TShopCategory>> delShopCategory(String id){
        TShopCategory tShopCategory = tShopCategoryService.getById(id);
        tShopCategoryService.removeById(id);
        return RtnMessageUtils.buildSuccess(tShopCategoryService.list(new QueryWrapper<TShopCategory>().eq("shop_id",tShopCategory.getShopId())));
    }
    /**
     * 店铺商品信息
     * */
    @RequestMapping("getShopProduct")
    @ResponseBody
    public RtnMessage<TShopProduct> getShopProduct(String productId){
        TShopProduct shopProduct= tShopProductService.getById(productId);
        return RtnMessageUtils.buildSuccess(shopProduct);
    }
    /**
     * 店铺商品信息
     * */
    @RequestMapping("saveShopProduct")
    @ResponseBody
    public RtnMessage<List<TShopProduct>> saveShopProduct(@RequestBody TShopProduct tShopProduct){
        logAllRequestParams();
        //信息填写到商品时开店
        TShop tShop = tShopService.getById(tShopProduct.getShopId());
        if("0".equals(tShop.getShopState())){
            tShop.setShopState("0");
            tShopService.saveOrUpdate(tShop);
        }
        if(StringUtilsEx.isBlank(tShopProduct.getId())){
            tShopProduct.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
            tShopProduct.setSaleTotal(0);
            tShopProduct.setStarTotal(0);

        }else {
            tShopProduct.preUpdate(new User(wxUserService.getCurrentWxUser().getNickName()));
        }
        tShopProductService.saveOrUpdate(tShopProduct);
        //保存商品成分
        List<TProductComponent> oldList = tProductComponentService.findByProductId(tShopProduct.getId());
        List<TProductComponent> newList = tShopProduct.getComponentList();
        //判断删除
        int count = 0;
        if(CollectionUtils.isNotEmpty(oldList)){
            for (TProductComponent oldComponent : oldList) {
                if(CollectionUtils.isNotEmpty(newList)){
                    for (TProductComponent newComponent : newList) {
                        if(!oldComponent.getId().equals(newComponent.getId())){
                            count++;
                        }
                    }
                }else {
                    tProductComponentService.removeById(oldComponent.getId());
                }
                if(count == newList.size()){
                    tProductComponentService.removeById(oldComponent.getId());
                }
                count = 0;
            }
        }
        for (TProductComponent tProductComponent : newList) {
            if(StringUtilsEx.isBlank(tProductComponent.getId())){
                tProductComponent.setId(UUIDGenerator.generate());
                tProductComponent.setProductId(tShopProduct.getId());
                tProductComponent.setDelFlag("0");
            }
        }
        tProductComponentService.saveOrUpdateBatch(newList);
        return RtnMessageUtils.buildSuccess(tShopProductService.list(new QueryWrapper<TShopProduct>().eq("shop_id",tShopProduct.getShopId())));
    }
    @RequestMapping("delShopProduct")
    @ResponseBody
    public RtnMessage<List<TShopCategory>> delShopProduct(String id,String shopId){
        tShopProductService.removeById(id);
        return RtnMessageUtils.buildSuccess(tShopCategoryService.getShopCategory(shopId));
    }

    @RequestMapping("productStar")
    @ResponseBody
    public RtnMessage<TShopProduct> productStar(String productId){
        TShopProduct tShopProduct = tShopProductService.getById(productId);
        tShopProduct.setStarTotal(tShopProduct.getStarTotal() + 1);
        tShopProductService.saveOrUpdate(tShopProduct);
        return RtnMessageUtils.buildSuccess(tShopProduct);
    }

    @RequestMapping("productStarDel")
    @ResponseBody
    public RtnMessage<TShopProduct> productStarDel(String productId){
        TShopProduct tShopProduct = tShopProductService.getById(productId);
        tShopProduct.setStarTotal(tShopProduct.getStarTotal() - 1);
        tShopProductService.saveOrUpdate(tShopProduct);
        return RtnMessageUtils.buildSuccess(tShopProduct);
    }

}
