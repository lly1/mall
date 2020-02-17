package com.mall.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mall.entity.user.User;
import com.mall.service.user.UserService;
import com.mall.wxshop.entity.user.WxUserInfo;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author liangjunhao
 * @Description 新增和更新数据时自动填补基础数据
 * @Date 2020-02-06
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    @Autowired
    private UserService userService;

    @Override
    public void insertFill(MetaObject metaObject) {
        User user = userService.getCurrentUser();
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("createBy", user.getUsername(), metaObject);
        this.setFieldValByName("updateBy", user.getUsername(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = userService.getCurrentUser();
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", user.getUsername(), metaObject);
    }
}
