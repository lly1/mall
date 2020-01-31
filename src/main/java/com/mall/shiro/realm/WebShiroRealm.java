package com.mall.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.entity.user.Role;
import com.mall.service.role.RoleService;
import com.mall.shiro.WebToken;
import com.mall.constant.Constants;
import com.mall.entity.user.User;
import com.mall.service.user.UserService;
import com.mall.utils.CommonUtil;
import com.mall.utils.cache.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lly
 */
public class WebShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(WebShiroRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("shiro授权");
        RoleService roleService = (RoleService) SpringContextUtil.getBean("RoleService");
        User user = (User)principalCollection;
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给该用户设置角色，角色信息存在 t_role 表中取
        List<Role> roles = roleService.getUserRoles(user.getId());
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                authorizationInfo.addRole(role.getRoleName());
            }
        }
        return authorizationInfo;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---------------- 执行 WebShiro 凭证认证 ----------------------");
        UserService userService = (UserService) SpringContextUtil.getBean("UserService");
        RoleService roleService = (RoleService) SpringContextUtil.getBean("RoleService");
        WebToken token = (WebToken) authenticationToken;
        //获取登录用户名
        String username= token.getUsername();
        // 得到密码
        String password = new String((char[]) token.getCredentials());
        //查询用户名称
        User user = userService.getOne(new QueryWrapper<User>().eq("username",username));
        if (CommonUtil.isNotBlank(user)) {
            if (user.getPassword().equals(password)) {
                //查询用户下所有权限
                List<Role> roles = roleService.getUserRoles(user.getId());
                StringBuilder roleId = new StringBuilder();
                if(!CollectionUtils.isEmpty(roles)){
                    for (Role role : roles) {
                        roleId.append(role.getRoleId()).append(",");
                    }
                }
                user.setRoleId(roleId.toString());
                Subject currentUser = SecurityUtils.getSubject();
                Session session = currentUser.getSession();
                session.setAttribute(Constants.USER_SESSION, user);
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
