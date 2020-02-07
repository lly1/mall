package com.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnPageInfo;
import com.mall.dao.user.UserMapper;
import com.mall.entity.user.User;
import com.mall.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通用户 user.type=0
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/index.WS")
    public String index() {
        return "views/sys/user";
    }

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<User> findPage(FrontPage<User> page) throws Exception {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("delFlag","0");
        Page<User> userPage = userMapper.selectPage(page.getPagePlus(), userQueryWrapper);

        return new RtnPageInfo<>(userPage);
    }

    @RequestMapping(value = {"/list","/listWS"})
    @ResponseBody
    public List<User> list() throws Exception {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("delFlag","0");
        List<User> users = userMapper.selectList(userQueryWrapper);
        if (CommonUtil.isNotBlank(users)) {
            return users;
        }
        return new ArrayList<>();
    }

}
