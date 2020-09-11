package com.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnPageInfo;
import com.mall.api.entity.wx.order.TOrder;
import com.mall.provider.service.wx.service.order.TOrderDetailService;
import com.mall.provider.service.wx.service.order.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TOrderService orderService;
    @Autowired
    private TOrderDetailService orderDetailService;

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
