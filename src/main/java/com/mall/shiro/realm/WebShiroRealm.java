package com.mall.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.shiro.WebToken;
import com.mall.constant.Constants;
import com.mall.entity.user.User;
import com.mall.service.user.UserService;
import com.mall.utils.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//实现AuthorizingRealm接口用户用户认证
public class WebShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(WebShiroRealm.class);

    //用于用户查询
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        System.out.println("principalCollection");
        return null;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }
    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        WebToken token = (WebToken) authenticationToken;
        //获取登录用户名
        String username= token.getUsername();
        // 得到密码
        String password = new String((char[]) token.getCredentials());
        //查询用户名称
        User user = userService.getOne(new QueryWrapper<User>().eq("username",username));
        if (CommonUtil.isNotBlank(user)) {
            if (user.getPassword().equals(password)) {
                Subject currentUser = SecurityUtils.getSubject();
                Session session = currentUser.getSession();
                session.setAttribute(Constants.User_Session, user);
                return new SimpleAuthenticationInfo(user, password,
                        getName());
            } else {
                throw new IncorrectCredentialsException("密码错误！");
            }
        } else {
            throw new UnknownAccountException("用户名不存在！");
        }
    }

}
