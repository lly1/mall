package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.component.RtnComponent;
import com.mall.entity.component.TComponent;
import com.mall.entity.component.TComponentCategory;
import com.mall.entity.component.TProductComponent;
import com.mall.service.component.TComponentCategoryService;
import com.mall.service.component.TComponentService;
import com.mall.service.component.TProductComponentService;
import com.mall.utils.RtnMessageUtils;
import com.mall.wxshop.entity.order.TOrder;
import com.mall.wxshop.entity.order.TOrderDetail;
import com.mall.wxshop.entity.user.WxUserInfo;
import com.mall.wxshop.service.order.TOrderDetailService;
import com.mall.wxshop.service.order.TOrderService;
import com.mall.wxshop.service.user.WxUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lly
 */
@Controller
@RequestMapping(value = "/api/component")
public class WxComponentController extends BaseController {
    @Resource
    private TComponentCategoryService tComponentCategoryService;
    @Resource
    private TComponentService tComponentService;
    @Resource
    private TProductComponentService tProductComponentService;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private TOrderService tOrderService;
    @Resource
    private TOrderDetailService tOrderDetailService;
    private static  List<RtnComponent> rtnComponents = new LinkedList<>();

    @RequestMapping("getComponent")
    @ResponseBody
    public RtnMessage<List<TComponentCategory>> getComponent(){
        List<TComponentCategory> componentCategory = tComponentCategoryService.getComponent();
        return RtnMessageUtils.buildSuccess(componentCategory);
    }

    @RequestMapping("getProductComponent")
    @ResponseBody
    public RtnMessage<List<TProductComponent>> getProductComponent(String productId){
        List<TProductComponent> productComponentList = tProductComponentService.findByProductId(productId);
        return RtnMessageUtils.buildSuccess(productComponentList);
    }

    @RequestMapping("getUserComponent")
    @ResponseBody
    public RtnMessage<List<RtnComponent>> getUserComponent(){
        //进方法清空list
        rtnComponents.clear();
        WxUserInfo userInfo = wxUserService.getCurrentWxUser();
        //查询用户的订单
        List<TOrder> orderList = tOrderService.list(new QueryWrapper<TOrder>().eq("user_id",userInfo.getId()).eq("order_status",3));
        orderList.parallelStream().forEach(order -> tOrderDetailService.findDetailByOrderId(order.getId()).parallelStream().forEach(tOrderDetail -> {
                //查询成分表运算
                tProductComponentService.findByProductId(tOrderDetail.getProductId()).parallelStream().forEach(tProductComponent -> {
                    TComponent tComponent = tComponentService.getById(tProductComponent.getComponentId());
                    synchronized (this){
                        if(CollectionUtils.isEmpty(rtnComponents)){
                            calculate(tComponent,tProductComponent,tOrderDetail);
                        }else {
                            calculateAdd(tComponent,tProductComponent,tOrderDetail);
                        }
                    }
                });
            }));
        return RtnMessageUtils.buildSuccess(rtnComponents);
    }
    private void calculate(TComponent tComponent, TProductComponent tProductComponent, TOrderDetail tOrderDetail){
        Field[] fields = tComponent.getClass().getDeclaredFields();
        for (Field field : fields) {
            RtnComponent rtnComponent = new RtnComponent();
            switch (field.getName()){
                default:
                    continue;
                case "protein":{
                    rtnComponent.setName("蛋白质");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getProtein() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "fat":{
                    rtnComponent.setName("脂肪");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getFat() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "carbohydrate":{
                    rtnComponent.setName("碳水化合物");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getCarbohydrate() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "calorie":{
                    rtnComponent.setName("卡路里");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getCalorie() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "salt":{
                    rtnComponent.setName("无机盐");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getSalt() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "calcium":{
                    rtnComponent.setName("钙");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getCalcium() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "phosphorus":{
                    rtnComponent.setName("磷");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getPhosphorus() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
                case "iron":{
                    rtnComponent.setName("铁");
                    rtnComponent.setValue((tProductComponent.getTotal()/100)* tComponent.getIron() * tOrderDetail.getBuyNum());
                    rtnComponents.add(rtnComponent);
                    break;
                }
            }
        }
    }

    private void calculateAdd(TComponent tComponent, TProductComponent tProductComponent, TOrderDetail tOrderDetail){
        for (RtnComponent rtnComponent : rtnComponents) {
            switch (rtnComponent.getName()){
                case "蛋白质":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getProtein() * tOrderDetail.getBuyNum());
                    break;
                }
                case "脂肪":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getFat() * tOrderDetail.getBuyNum());
                    break;
                }
                case "碳水化合物":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getCarbohydrate() * tOrderDetail.getBuyNum());
                    break;
                }
                case "卡路里":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getCalorie() * tOrderDetail.getBuyNum());
                    break;
                }
                case "无机盐":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getSalt() * tOrderDetail.getBuyNum());
                    break;
                }
                case "钙":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getCalcium() * tOrderDetail.getBuyNum());
                    break;
                }
                case "磷":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getPhosphorus() * tOrderDetail.getBuyNum());
                    break;
                }
                case "铁":{
                    rtnComponent.setValue(rtnComponent.getValue() + (tProductComponent.getTotal()/100)* tComponent.getIron() * tOrderDetail.getBuyNum());
                    break;
                }default:{
                    logger.error("没有",rtnComponent.getName());
                }
            }
        }
    }

}
