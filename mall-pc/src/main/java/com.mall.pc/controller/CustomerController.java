package com.mall.controller;

import com.mall.common.BaseController;
import com.mall.common.RtnMessage;
import com.mall.api.entity.user.User;
import com.mall.service.user.UserService;
import com.mall.api.utils.RtnMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = {"customer","customerWX"})
public class CustomerController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        List userList = userService.getUserByRoleId(0);
        return null;
    }
    @RequestMapping(value = "register")
    @ResponseBody
    public RtnMessage register(User user){
        try{
            userService.save(user);
            return RtnMessageUtils.buildSuccess("注册成功");
        }catch (Exception e){
            e.printStackTrace();
            return RtnMessageUtils.buildSuccess("注册失败");
        }
    }
}
