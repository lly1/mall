package com.mall.filter;

import com.alibaba.fastjson.JSON;
import com.mall.common.RtnMessage;
import com.mall.common.UsernamePasswordToken;
import com.mall.constant.ErrorType;
import com.mall.entity.domain.LoginParam;
import com.mall.entity.domain.RtnLogin;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;
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
public class WebAuthenticationFilter extends AuthenticatingFilter{
    private static Logger logger = LoggerFactory.getLogger(WebAuthenticationFilter.class);
    protected String getUsername(ServletRequest request) {
        return (String) request.getAttribute("username");
    }

    protected String getPassword(ServletRequest request) {
        return (String) request.getAttribute("password");
    }

    protected String getLoginSrc(ServletRequest request) {
        return (String) request.getAttribute("loginSrc");
    }

    protected String getCaptcha(ServletRequest request) {
        return (String) request.getAttribute("verifyCode");
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        String body = IOUtils.toString(request.getInputStream());
        LoginParam login;
        if(StringUtils.isBlank(body)){
            login = getDataFromRequest(request);
        }else{
            login = JSON.parseObject(body,LoginParam.class);
        }
        if(login == null){
            return new UsernamePasswordToken();
        }
        String username = login.getLoginName();
        String password = login.getPassword();
        String loginSrc = login.getLoginSrc();
        boolean rememberMe = isRememberMe(request);
        String host = StringUtilsEx.getRemoteAddr((HttpServletRequest) request);
        String captcha = getCaptcha(request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha,loginSrc);
    }

    private LoginParam getDataFromRequest(ServletRequest request){
        String loginName = getUsername(request);
        String loginSrc = getLoginSrc(request);
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginSrc)){
            return null;
        }

        LoginParam reqLogin = new LoginParam();
        reqLogin.setLoginName(loginName);
        reqLogin.setLoginSrc(loginSrc);
        return reqLogin;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            //前面已经处理过
            return false;
        }
        RtnMessage rtn = RtnMessageUtils.buildError(ErrorType.NEEDLOGIN);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(JSON.toJSONString(rtn));
        logger.error("WebAuthenticationFilter.onAccessDenied,{}", JSON.toJSONString(rtn));
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
        try{
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
            RtnMessage<RtnLogin> rtn;
            if(usernamePasswordToken.getFailCode() != null){
                 rtn = RtnMessageUtils.buildError(usernamePasswordToken.getFailCode());
            }else{
                rtn = RtnMessageUtils.buildError(ErrorType.BAD_USER_OR_PASS);
            }
            logger.error("登录失败:{}", JSON.toJSONString(token));
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(rtn));
            logger.error("WebAuthenticationFilter.onLoginFailure,{}", JSON.toJSONString(rtn));
        }catch (Exception ee){
            logger.error("onLoginFailure Exception",ee);
        }finally {
            return false;
        }
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try{
            if (this.isLoginRequest(request, response)) {
                return this.executeLogin(request, response);
            }else{
                return super.isAccessAllowed(request, response, mappedValue);
            }
        }catch (Exception e){
            logger.error("isAccessAllowed 异常",e);
            return false;
        }
    }
}
