package com.mall.wxshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.entity.component.TComponentCategory;
import com.mall.entity.component.TProductComponent;
import com.mall.service.component.TComponentCategoryService;
import com.mall.service.component.TComponentService;
import com.mall.service.component.TProductComponentService;
import com.mall.utils.RtnMessageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

}
