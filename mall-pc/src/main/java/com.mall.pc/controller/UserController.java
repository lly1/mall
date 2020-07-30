package com.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnMessage;
import com.mall.common.RtnPageInfo;
import com.mall.api.entity.user.Role;
import com.mall.api.entity.user.User;
import com.mall.api.entity.user.UserRole;
import com.mall.service.role.RoleService;
import com.mall.service.role.UserRoleService;
import com.mall.service.user.UserService;
import com.mall.api.utils.CommonUtil;
import com.mall.api.utils.RtnMessageUtils;
import com.mall.api.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 普通用户 user.type=0
 * @author lly
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index.WS")
    public String index() {
        return "views/sys/user";
    }

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<User> findPage(FrontPage<User> page) {
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

    @RequestMapping(value = "/save")
    @ResponseBody
    public RtnMessage save(User user, String pageType) {
        this.logAllRequestParams();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User u = this.userService.getOne(queryWrapper);
        try {
            if (pageType.equals("add")) {
                if (CommonUtil.isBlank(u)) {
                    u = new User();
                    u.setId(UUIDGenerator.generate());
                    u.setUsername(user.getUsername());
                    u.setPassword(user.getPassword());
                    u.setRoleId(user.getRoleId());
                    u.setPhone(user.getPhone());
                    //保存user
                    userService.save(u);
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(user.getRoleId());
                    userRole.setUserId(u.getId());
                    //保存userRole
                    userRoleService.save(userRole);
                } else {
                    return RtnMessageUtils.buildFailed("登录名已存在，请重新输入");
                }
            } else {
                u.setUsername(user.getUsername());
                u.setPassword(user.getPassword());
                u.setRoleId(user.getRoleId());
                u.setPhone(user.getPhone());
                //更新user
                userService.updateById(u);
                //更新userRole
                UserRole userRole = new UserRole();
                userRole.setRoleId(user.getRoleId());
                UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("user_id", u.getId());
                userRoleService.update(userRole, updateWrapper);
            }
            return RtnMessageUtils.buildSuccess("保存成功");
        }catch (Exception e){
            /*将异常打印到日志*/
            this.logger.error(e.getMessage());
            e.printStackTrace();
            return RtnMessageUtils.buildFailed("保存失败");
        }
    }


}
