package com.mall.pc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mall.api.dubbo.pc.DubboUserService;
import com.mall.api.exception.ServiceException;
import com.mall.pc.common.BaseController;
import com.mall.api.entity.base.RtnMessage;
import com.mall.api.entity.pc.user.User;
import com.mall.api.utils.RtnMessageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = {"customer","customerWX"})
public class CustomerController extends BaseController {
    @Reference
    private DubboUserService userService;

    @RequestMapping("/")
    public String index(){
        List userList = userService.getUserByRoleId(0);
        return null;
    }

    @RequestMapping(value = "register")
    @ResponseBody
    public RtnMessage register(User user){
        try{
            if(userService.save(user)){
                return RtnMessageUtils.buildSuccess("注册成功");
            }else {
                throw new ServiceException("注册失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return RtnMessageUtils.buildSuccess("注册失败");
        }
    }
}
