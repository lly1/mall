package com.mall.provider.dubbo.pc.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.mall.api.dubbo.pc.DubboUserService;
import com.mall.api.entity.pc.user.User;
import com.mall.provider.service.pc.user.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service(protocol = {"dubbo"}, timeout = 30000)
public class DubboUserServiceImpl implements DubboUserService {
    @Resource
    private UserService userService;

    @Override
    public List<User> getAll() {
        return userService.getAll();
    }

    @Override
    public List<User> getUserByRoleId(Integer roleId) {
        return userService.getUserByRoleId(roleId);
    }

    @Override
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Override
    public boolean save(User user) {
        return userService.save(user);
    }
}
