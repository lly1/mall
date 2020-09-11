package com.mall.provider.service.pc.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.entity.pc.user.User;
import com.mall.provider.dao.user.UserMapper;
import com.mall.provider.service.pc.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lly
 */
@Service("UserService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> getAll() {
        return list(new QueryWrapper<User>().eq("delFlag",0));
    }

    @Override
    public List<User> getUserByRoleId(Integer roleId) {
        return baseMapper.getUserByRoleId(roleId);
    }

    @Override
    public User getCurrentUser() {
        return getLoginUser();
    }

    private User getLoginUser() {
        User user = new User();
        try {
            Subject subject = SecurityUtils.getSubject();
            user = (User) subject.getPrincipal();
            if (user == null) {
                return null;
            }
            return user;
        } catch (Exception e) {
            logger.error("获取当前用户异常", e);
        }
        return user;
    }
}
