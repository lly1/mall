package com.mall.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnMessage;
import com.mall.common.RtnPageInfo;
import com.mall.dao.user.UserMapper;
import com.mall.entity.user.Role;
import com.mall.entity.user.User;
import com.mall.entity.user.UserRole;
import com.mall.service.role.RoleService;
import com.mall.service.role.UserRoleService;
import com.mall.service.user.UserService;
import com.mall.utils.CommonUtil;
import com.mall.utils.RtnMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 普通用户 user.type=0
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index.WS")
    public String index() {
        return "views/sys/user";
    }

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<User> findPage(FrontPage<User> page) throws Exception {
        Page<User> userPage = userService.page(page.getPagePlus());
        List<User> userList = userPage.getRecords();
        for (User user : userList){
            Role role = roleService.getRoleNameByUserId(user.getId());
            if (null == role){
                user.setRoleName("");
            }else {
                user.setRoleName(role.getRoleName());
            }
        }
        return new RtnPageInfo<>(userPage);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<User> list() throws Exception {
        List<User> users = userService.list();
        if (CommonUtil.isNotBlank(users)) {
            return users;
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public RtnMessage<String> save(User user, String pageType, HttpSession session) throws Exception {
        this.logAllRequestParams();
        User sessionUser = (User) session.getAttribute("userSession");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User u = this.userService.getOne(queryWrapper);
        if (pageType.equals("add")) {
            if (CommonUtil.isBlank(u)) {
                u = new User();
                u.setUsername(user.getUsername());
                u.setPassword(user.getPassword());
                u.setRoleId(user.getRoleId());
                u.setPhone(user.getPhone());
                u.setCreateTime(new Date());
                u.setCreateBy(sessionUser.getUsername());
            } else {
                return RtnMessageUtils.buildFailed("登录名已存在，请重新输入");
            }
        } else {
            u.setUsername(user.getUsername());
            u.setPassword(user.getPassword());
            u.setRoleId(user.getRoleId());
            u.setPhone(user.getPhone());
        }
        try {
            this.userService.saveOrUpdate(u);
            List<User> userList = new ArrayList<>();
            userList.add(u);
            return RtnMessageUtils.buildSuccess("保存成功");
        }catch (Exception e){
            /*将异常打印到日志*/
            this.logger.error(e.getMessage());
            e.printStackTrace();
            return RtnMessageUtils.buildFailed("保存失败");
        }
    }


}
