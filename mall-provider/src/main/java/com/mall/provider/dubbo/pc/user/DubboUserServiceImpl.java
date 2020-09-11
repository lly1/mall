package com.mall.provider.dubbo.pc.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.dubbo.pc.DubboUserService;
import com.mall.api.entity.pc.user.User;
import com.mall.provider.dao.user.UserMapper;

@Service(protocol = {"dubbo"}, timeout = 30000)
public class DubboUserServiceImpl extends ServiceImpl<UserMapper,User> implements DubboUserService {

}
