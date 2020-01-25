package com.mall.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.constant.Constants;
import com.mall.shiro.WxToken;
import com.mall.utils.CommonUtil;
import com.mall.wxshop.entity.WxUserInfo;
import com.mall.wxshop.service.user.WxUserService;
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
public class WxShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(WxShiroRealm.class);

    //用于用户查询
    @Autowired
    private WxUserService wxUserService;

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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---------------- 执行 WxShiro 凭证认证 ----------------------");
        WxToken token = (WxToken) authenticationToken;
        //获取openId
        String openId = (String)token.getPrincipal();
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setOpenId(openId);
        //查询用户，不存在就插入
        WxUserInfo user = wxUserService.loginOrRegisterConsumer(wxUserInfo);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(Constants.User_Session, user);
        return new SimpleAuthenticationInfo(user, "ok",
                getName());
    }

}
