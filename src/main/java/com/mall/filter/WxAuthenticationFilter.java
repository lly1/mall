package com.mall.filter;

import com.alibaba.fastjson.JSON;
import com.mall.common.RtnMessage;
import com.mall.constant.ErrorType;
import com.mall.shiro.WxToken;
import com.mall.utils.RtnMessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lly
 */
public class WxAuthenticationFilter extends AuthenticatingFilter{
    private static Logger logger = LoggerFactory.getLogger(WxAuthenticationFilter.class);
    private String getOpenId(ServletRequest request) {
        return (String) request.getAttribute("openId");
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        String openId = getOpenId(request);
        return new WxToken(openId);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        RtnMessage rtn = RtnMessageUtils.buildError(ErrorType.NEEDLOGIN);
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("application/json;charset=utf-8");
        servletResponse.getWriter().println(JSON.toJSONString(rtn));
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String param = "";
        if(!StringUtils.isBlank(request.getQueryString())){
            param = "?" + request.getQueryString();
        }
        logger.info("url= " + request.getRequestURL() + param);
        logger.error("onAccessDenied,{}", JSON.toJSONString(rtn));
        //;如果返回true表示需要继续处理;如果返回false表示该拦截器实例已经处理了,将直接返回即可
        return false;
    }

    @Override
    public boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return super.executeLogin(request,response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        logger.info("登录成功:{}", JSON.toJSONString(token));
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        logger.error("登录失败:{}", JSON.toJSONString(token));
        return false;
    }
}
