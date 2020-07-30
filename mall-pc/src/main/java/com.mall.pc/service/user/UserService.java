package com.mall.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.user.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getAll();
    List<User> getUserByRoleId(Integer roleId);
    /**
     * @Description 获取当前用户
     **/
    User getCurrentUser();
}
