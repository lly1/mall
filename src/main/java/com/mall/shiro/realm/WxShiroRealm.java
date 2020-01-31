package com.mall.shiro.realm;

import com.mall.constant.Constants;
import com.mall.shiro.WxToken;
import com.mall.utils.cache.SpringContextUtil;
import com.mall.wxshop.entity.user.WxUserInfo;
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

/**
 * @author lly
 */
public class WxShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(WxShiroRealm.class);

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
        WxUserService wxUserService = (WxUserService) SpringContextUtil.getBean("WxUserService");
        WxToken token = (WxToken) authenticationToken;
        //获取openId
        String openId = (String)token.getPrincipal();
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setOpenId(openId);
        //查询用户，不存在就插入
        WxUserInfo user = null;
        try {
            user = wxUserService.loginOrRegisterCustomer(wxUserInfo);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(Constants.USER_SESSION, user);
        return new SimpleAuthenticationInfo(user, "ok",
                getName());
    }

}
