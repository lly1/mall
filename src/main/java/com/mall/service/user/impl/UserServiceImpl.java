package com.mall.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.user.UserMapper;
import com.mall.entity.domain.Principal;
import com.mall.entity.user.User;
import com.mall.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    private Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }


    private User getLoginUser() {
        User user = new User();
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal == null) {
                return null;
            }
            try {
                Session session = getSession();
                if (session != null) {
                    principal.setSessionId((String) session.getId());
                }
            } catch (Exception e) {
                principal.setSessionId("");
            }
            BeanUtils.copyProperties(principal,user);
            return user;
        } catch (Exception e) {
            logger.error("获取当前用户异常", e);
        }
        return user;
    }
}
