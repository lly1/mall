package com.mall.controller;

import com.mall.common.BaseController;
import com.mall.entity.menu.Menu;
import com.mall.entity.menu.SidebarMenu;
import com.mall.service.menu.MenuService;
import com.mall.utils.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = {"role","roleWX"})
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;
    /**
     * 通过角色ID获取权限
     *
     * @return
     */
    @RequestMapping(value="/showSidebarMenu")
    @ResponseBody
    public List<SidebarMenu> showSidebarMenu(String roleId) {
        this.logAllRequestParams();
        List<Menu> resourceList = new LinkedList<>();
        String[] arg = roleId.split(",");
        if(arg.length > 1){
            for (String s : arg) {
                resourceList.addAll(this.menuService.getMenuByRole(s));
            }
        }
        return ResourceUtil.convertToSidebarMenuVo(resourceList);
    }
}
