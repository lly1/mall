package com.mall.pc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.api.dubbo.Wx.DubboWxUserService;
import com.mall.api.dubbo.pc.DubboRoleService;
import com.mall.api.entity.base.FrontPage;
import com.mall.api.entity.base.RtnPageInfo;
import com.mall.api.entity.pc.user.Role;
import com.mall.pc.common.BaseController;
import com.mall.api.entity.wx.user.WxUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/sys/wxUser")
public class UserWxController extends BaseController {

    @Reference
    private DubboWxUserService wxUserService;
    @Reference
    private DubboRoleService roleService;

    @RequestMapping("/index.WS")
    public String index() {
        return "views/sys/wxUser";
    }

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<WxUserInfo> findPage(FrontPage<WxUserInfo> page) {
        Page<WxUserInfo> userPage = wxUserService.page(page.getPagePlus());
        List<WxUserInfo> userList = userPage.getRecords();
        for (WxUserInfo user : userList){
            List<Role> roles = roleService.getUserRoles(user.getId());
            if(roles.size() > 1){
                List<String> roleIds = new LinkedList<>();
                //获取所有的角色，设置最大的角色为主要角色返回
                roles.parallelStream().forEach(item ->{
                    roleIds.add(item.getRoleId());
                });
                user.setRoleId(Collections.min(roleIds));
            }else {
                user.setRoleId(roles.get(0).getRoleId());
            }
        }
        return new RtnPageInfo<>(userPage);
    }
}
