package com.mall.pc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.api.dubbo.pc.DubboOrderDetailService;
import com.mall.api.dubbo.pc.DubboOrderService;
import com.mall.api.entity.base.FrontPage;
import com.mall.api.entity.base.RtnPageInfo;
import com.mall.api.entity.wx.order.TOrder;
import com.mall.pc.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author lly
 */
@Controller
@RequestMapping("/sys/order")
public class OrderController extends BaseController {

    @Reference
    private DubboOrderService orderService;
    @Reference
    private DubboOrderDetailService orderDetailService;

    @RequestMapping("/index.WS")
    public String index() {
        return "views/order/order";
    }

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<TOrder> findPage(FrontPage<TOrder> page) {
        List<TOrder> orderList = orderService.findOrder(page.getPagePlus());
        Page<TOrder> orderPage = page.getPagePlus();
        orderPage.setRecords(orderList);
        orderPage.setTotal(orderService.count());
        return new RtnPageInfo<>(orderPage);
    }

}
