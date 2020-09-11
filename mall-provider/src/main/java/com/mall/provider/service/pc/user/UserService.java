package com.mall.provider.service.pc.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.pc.user.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getAll();
    List<User> getUserByRoleId(Integer roleId);
    /**
     * @Description 获取当前用户
     **/
    User getCurrentUser();
}
