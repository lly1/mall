package com.mall.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.api.entity.pc.user.User;
import com.mall.api.utils.RtnMessageUtils;
import com.mall.api.utils.StringUtilsEx;
import com.mall.api.entity.wx.order.TOrder;
import com.mall.api.entity.wx.order.TOrderDetail;
import com.mall.api.entity.wx.sale.TCart;
import com.mall.api.entity.wx.sale.TComment;
import com.mall.api.entity.wx.shop.TShop;
import com.mall.api.entity.wx.shop.TShopProduct;
import com.mall.api.entity.wx.user.WxUserInfo;
import com.mall.provider.service.wx.service.order.TOrderDetailService;
import com.mall.provider.service.wx.service.order.TOrderService;
import com.mall.provider.service.wx.service.sale.TCartService;
import com.mall.provider.service.wx.service.sale.TCommentService;
import com.mall.provider.service.wx.service.shop.TCodeService;
import com.mall.provider.service.wx.service.shop.TShopProductService;
import com.mall.provider.service.wx.service.shop.TShopService;
import com.mall.provider.service.wx.service.user.WxUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by lly on 2019/12/2
 */
@Controller
@RequestMapping(value = "/api/order")
public class WxOrderController extends BaseController {

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("order-pool-%d").build();
    private static ExecutorService pool = new ThreadPoolExecutor(1, 5,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(20), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Resource
    private WxUserService wxUserService;
    @Resource
    private TCartService tCartService;
    @Resource
    private TOrderService tOrderService;
    @Resource
    private TOrderDetailService tOrderDetailService;
    @Resource
    private TCodeService tCodeService;
    @Resource
    private TShopService tShopService;
    @Resource
    private TShopProductService tShopProductService;
    @Resource
    private TCommentService tCommentService;

    @RequestMapping("/getOrderById")
    @ResponseBody
    public RtnMessage<TOrder> getOrderById(String id) {
        TOrder tOrder = tOrderService.findOrderById(id);
        tOrder.setDetailList(tOrderDetailService.findDetailByOrderId(tOrder.getId()));
        return RtnMessageUtils.buildSuccess(tOrder);
    }

    @RequestMapping("/getShopPayOrder")
    @ResponseBody
    public RtnMessage<WxUserInfo> getShopPayOrder(String shopId) {
        WxUserInfo wxUserInfo = wxUserService.getCurrentWxUser();
        List<TOrder> orderList = tOrderService.list(new QueryWrapper<TOrder>()
                .eq("shop_id",shopId)
                .eq("order_status",1));
        wxUserInfo.setNewOrder(orderList.size());
        return RtnMessageUtils.buildSuccess(wxUserInfo);
    }

    @RequestMapping("/getUserOrder0")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getUserOrder0(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByUserId0(tOrder.getUserId(),orderPage);
        orderList.parallelStream().forEach(order ->{
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
        orderList.parallelStream().forEach(order ->{
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
        orderList.parallelStream().forEach(order ->{
            order.setDetailList(tOrderDetailService.findDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("/getShopOrder0")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getShopOrder0(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByShopId0(tOrder.getShopId(),orderPage);
        orderList.parallelStream().forEach(order ->{
            order.setDetailList(tOrderDetailService.findShopDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("/getShopOrder1")
    @ResponseBody
    public RtnMessage<Page<TOrder>> getShopOrder1(TOrder tOrder) {
        Page<TOrder> orderPage = tOrder.buildPage();
        List<TOrder> orderList = tOrderService.findOrderByShopId1(tOrder.getShopId(),orderPage);
        orderList.parallelStream().forEach(order ->{
            order.setDetailList(tOrderDetailService.findShopDetailByOrderId(order.getId()));
        });
        orderPage.setRecords(orderList);
        return RtnMessageUtils.buildSuccess(orderPage);
    }
    @RequestMapping("acceptOrder")
    @ResponseBody
    public RtnMessage<String> acceptOrder(String id){
        TOrder tOrder = tOrderService.getById(id);
        int code =  tCodeService.getCodeByShopId(tOrder.getShopId());
        int success = tCodeService.updateCode(code,tOrder.getShopId());
        if(success == 1){
            tOrder.setOrderStatus(2);
            tOrder.setRtnCode(code);
            boolean flag = tOrderService.saveOrUpdate(tOrder);
            if(!flag){
                return RtnMessageUtils.buildFailed("接单失败");
            }
        }
        return RtnMessageUtils.buildSuccess("接单成功");
    }
    @RequestMapping("refuseOrder")
    @ResponseBody
    public RtnMessage<String> refuseOrder(String id){
        TOrder tOrder = tOrderService.getById(id);
        tOrder.setOrderStatus(-1);
        tOrderService.saveOrUpdate(tOrder);
        return RtnMessageUtils.buildSuccess("取消成功");
    }

    @RequestMapping("delOrder")
    @ResponseBody
    public RtnMessage<String> delShopProduct(String id){
        TOrder tOrder = tOrderService.getById(id);
        if(tOrder.getOrderStatus() < 3){
            return RtnMessageUtils.buildFailed("只能删除已完成的订单！");
        }else {
            boolean flag = tOrderService.removeById(id);
            if(flag){
                tOrderDetailService.remove(new QueryWrapper<TOrderDetail>().eq("order_id",id));
            }else {
                RtnMessageUtils.buildFailed("删除失败");
            }
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
        int count = 0;
        for (TCart cart : cartList) {
            TOrderDetail tOrderDetail = new TOrderDetail();
            tOrderDetail.setBuyNum(cart.getBuyNum());
            tOrderDetail.setOrderId(tOrder.getId());
            tOrderDetail.setProductId(cart.getProduct().getId());
            tOrderDetail.preInsert(new User(wxUserService.getCurrentWxUser().getNickName()));
            //总共买多少
            count += cart.getBuyNum();
            detailList.add(tOrderDetail);
        }
        tOrder.setBuyTotal(count);
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
    public RtnMessage UserPay(String id,String remarks,String pass) {
        if(StringUtilsEx.isBlank(pass)){
            return RtnMessageUtils.buildFailed("密码不能未空!");
        }
        WxUserInfo userInfo = wxUserService.getCurrentWxUser();
        if(!pass.equals(userInfo.getPayPass())){
            return RtnMessageUtils.buildFailed("密码错误!");
        }
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
        tOrder.setDetailList(tOrderDetailService.findDetailByOrderId(tOrder.getId()));
        return RtnMessageUtils.buildSuccess(tOrder);
    }

    @RequestMapping("/buyAgain")
    @ResponseBody
    public RtnMessage<String> buyAgain(@RequestBody List<TOrderDetail> detailList) {
        WxUserInfo userInfo = wxUserService.getCurrentWxUser();
        detailList.parallelStream().forEach(item ->{
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
    @RequestMapping("/rateOrder")
    @ResponseBody
    public RtnMessage<String> rateOrder(TComment comment) {
        WxUserInfo userInfo = wxUserService.getCurrentWxUser();
        comment.preInsert(new User(userInfo.getNickName()));
        boolean flag = tCommentService.save(comment);
        if(flag) {
            //更新订单
            TOrder tOrder = tOrderService.getById(comment.getOrderId());
            tOrder.preUpdate(new User(userInfo.getNickName()));
            tOrder.setIsRate("1");
            boolean flag1 =  tOrderService.saveOrUpdate(tOrder);
            //更新门店评分
            TShop tShop = tShopService.getById(comment.getShopId());
            tShop.preUpdate(new User(userInfo.getNickName()));
            tShop.setShopStar(Double.valueOf(String.format("%.1f",(tShop.getShopStar()+comment.getStar())/2)));
            boolean flag2 =tShopService.saveOrUpdate(tShop);
            if(!(flag1&&flag2)){
                return RtnMessageUtils.buildFailed("评价失败");
            }
        }
        return RtnMessageUtils.buildSuccess("评价成功");
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public RtnMessage<String> confirm(String orderId) {
        TOrder tOrder = tOrderService.getById(orderId);
        tOrder.setOrderStatus(3);
        boolean flag  = tOrderService.saveOrUpdate(tOrder);
        if(flag){
            //异步添加店铺销量，商品减库存
            pool.execute(new AsyncThread(orderId));
        }
        return RtnMessageUtils.buildSuccess("收货成功!");
    }

    private class AsyncThread implements Runnable{
        private String orderId;
        public AsyncThread(String orderId) {
            this.orderId = orderId;
        }
        @Override
        public void run() {
            try{
                Thread.sleep(1000);
                //把订单查出来
                TOrder tOrder = tOrderService.findOrderById(orderId);
                TShop tShop = tShopService.getById(tOrder.getShopId());
                List<TOrderDetail> detailList = tOrderDetailService.findDetailByOrderId(tOrder.getId());
                //商品减库存，店铺算总共卖了多少
                for (TOrderDetail tOrderDetail : detailList) {
                   TShopProduct tShopProduct = tOrderDetail.getProduct();
                   tShopProduct.setProductStock(tShopProduct.getProductStock() - tOrderDetail.getBuyNum());
                   tShopProduct.setSaleTotal(tShopProduct.getSaleTotal() + tOrderDetail.getBuyNum());
                   //库存为0时自动下架
                   if(tShopProduct.getProductStock() == 0){
                       tShopProduct.setProductStatus("0");
                   }
                   tShopProductService.saveOrUpdate(tShopProduct);
                }
                tShop.setShopSale(tShop.getShopSale() + tOrder.getBuyTotal());
                tShopService.saveOrUpdate(tShop);
            }catch (Exception e){
                logger.error("异步操作失败");
            }

        }
    }
}
