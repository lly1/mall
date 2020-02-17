package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.user.User;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
import com.mall.wxshop.entity.order.TOrder;
import com.mall.wxshop.entity.order.TOrderDetail;
import com.mall.wxshop.entity.sale.TCart;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.wxshop.service.order.TOrderDetailService;
import com.mall.wxshop.service.order.TOrderService;
import com.mall.wxshop.service.sale.TCartService;
import com.mall.wxshop.service.user.WxUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lly on 2019/12/2
 */
@Controller
@RequestMapping(value = "/api/order")
public class WxOrderController extends BaseController {

    @Resource
    private WxUserService wxUserService;
    @Resource
    private TCartService tCartService;
    @Resource
    private TOrderService tOrderService;
    @Resource
    private TOrderDetailService tOrderDetailService;

    @RequestMapping("/getOrderById")
    @ResponseBody
    public RtnMessage<TOrder> getOrderById(String id) {
        TOrder tOrder = tOrderService.findOrderById(id);
        tOrder.setDetailList(tOrderDetailService.findDetailByOrderId(tOrder.getId()));
        return RtnMessageUtils.buildSuccess(tOrder);
    }

    @RequestMapping("/getUserOrder0")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getUserOrder0(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByUserId0(tOrder.getUserId(),orderPage);
        orderList.forEach(order ->{
            order.setDetailList(tOrderDetailService.findDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("/getUserOrder1")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getUserOrder1(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByUserId1(tOrder.getUserId(),orderPage);
        orderList.forEach(order ->{
            order.setDetailList(tOrderDetailService.findDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("/getUserOrder2")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getUserOrder2(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByUserId2(tOrder.getUserId(),orderPage);
        orderList.forEach(order ->{
            order.setDetailList(tOrderDetailService.findDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("/getShopOrder0")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getShopOrder0(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByShopId0(tOrder.getUserId(),orderPage);
        orderList.forEach(order ->{
            order.setDetailList(tOrderDetailService.findDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("/getShopOrder1")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getShopOrder1(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByShopId1(tOrder.getUserId(),orderPage);
        orderList.forEach(order ->{
            order.setDetailList(tOrderDetailService.findDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("delOrder")
    @ResponseBody
    public RtnMessage<String> delShopProduct(String id){
        boolean flag = tOrderService.removeById(id);
        if(flag){
            tOrderDetailService.remove(new QueryWrapper<TOrderDetail>().eq("order_id",id));
        }else {
            RtnMessageUtils.buildFailed("删除失败");
        }
        return RtnMessageUtils.buildSuccess("success");
    }

    @RequestMapping("createOrder")
    @ResponseBody
    public RtnMessage<TOrder> createOrder(TOrder tOrder){
        logAllRequestParams();
        List<TOrderDetail> detailList = new LinkedList<>();
        TCart tCart = new TCart();
        tCart.setShopId(tOrder.getShopId());
        tCart.setUserId(tOrder.getUserId());
        tOrder.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
        tOrder.setOrderStatus(0);
        tOrder.setIsRate("0");
        //查出这个人在这个人的购物车，然后创建订单明细
        List<TCart> cartList = tCartService.findCartById(tCart);
        cartList.forEach(item -> {
            TOrderDetail tOrderDetail = new TOrderDetail();
            tOrderDetail.setBuyNum(item.getBuyNum());
            tOrderDetail.setOrderId(tOrder.getId());
            tOrderDetail.setProductId(item.getProduct().getId());
            tOrderDetail.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
            detailList.add(tOrderDetail);
        });
        boolean flag = tOrderService.saveOrUpdate(tOrder);
        if(flag){
            tOrderDetailService.saveOrUpdateBatch(detailList);
        }else {
            return RtnMessageUtils.buildFailed(null);
        }
        return RtnMessageUtils.buildSuccess(tOrder);
    }
    @RequestMapping("/UserPay")
    @ResponseBody
    public RtnMessage<TOrder> UserPay(String id,String remarks) {
        TOrder tOrder = tOrderService.findOrderById(id);
        //已支付
        tOrder.setOrderStatus(1);
        tOrder.setRemarks(remarks);
        boolean flag = tOrderService.saveOrUpdate(tOrder);
        if(flag){
            //清空购物车
            TCart tCart = new TCart();
            tCart.setShopId(tOrder.getShopId());
            tCart.setUserId(tOrder.getUserId());
            tCartService.delAllCart(tCart);
        }else {
            RtnMessageUtils.buildFailed("支付失败");
        }
        return RtnMessageUtils.buildSuccess(tOrder);
    }

    @RequestMapping("/buyAgain")
    @ResponseBody
    public RtnMessage<String> buyAgain(@RequestBody List<TOrderDetail> detailList) {
        WxUserInfo userInfo = wxUserService.getCurrentWxUser();
        detailList.forEach(item->{
            TCart tCart = new TCart();
            tCart.preInsert(new User(userInfo.getNickName()));
            tCart.setUserId(userInfo.getId());
            tCart.setShopId(item.getProduct().getShopId());
            tCart.setProductId(item.getProduct().getId());
            tCart.setBuyNum(item.getBuyNum());
            tCartService.save(tCart);
        });
        return RtnMessageUtils.buildSuccess("success");
    }

}
