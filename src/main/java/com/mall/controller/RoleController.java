package com.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnPageInfo;
import com.mall.entity.user.Role;
import com.mall.entity.user.User;
import com.mall.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

	@RequestMapping(value ="/index.WS")
	public String index() {
		return "/views/sys/role";
	}

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<Role> findPage(FrontPage<Role> page) throws Exception {
        Page<Role> userPage = roleService.page(page.getPagePlus());
        return new RtnPageInfo<>(userPage);
    }



}
