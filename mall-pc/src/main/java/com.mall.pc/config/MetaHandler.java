package com.mall.pc.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mall.api.dubbo.pc.DubboUserService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


/**
 * @Author liangjunhao
 * @Description 新增和更新数据时自动填补基础数据
 * @Date 2020-02-06
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    @Reference
    private DubboUserService userService;

    @Override
    public void insertFill(MetaObject metaObject) {
        /*User user = userService.getCurrentUser();
        if(CommonUtil.isBlank(user)){
            return;
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("createBy", user.getUsername(), metaObject);
        this.setFieldValByName("updateBy", user.getUsername(), metaObject);*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        /*User user = userService.getCurrentUser();
        if(CommonUtil.isBlank(user)){
            return;
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", user.getUsername(), metaObject);*/
    }
}
