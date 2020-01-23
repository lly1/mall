package com.mall.filter;

import com.alibaba.fastjson.JSON;
import com.mall.common.RtnMessage;
import com.mall.common.UsernamePasswordToken;
import com.mall.constant.ErrorType;
import com.mall.constant.LoginSourceEnum;
import com.mall.entity.domain.RtnLogin;
import com.mall.utils.CommonUtil;
import com.mall.utils.RtnMessageUtils;
import com.mall.utils.StringUtilsEx;
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
    private String getUsername(ServletRequest request) {
        return (String) request.getAttribute("username");
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        String username = getUsername(request);
        String password = CommonUtil.DING_PASS;
        String loginSrc = LoginSourceEnum.WX.getIndex();
        boolean rememberMe = isRememberMe(request);
        String host = StringUtilsEx.getRemoteAddr((HttpServletRequest) request);

        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, null, loginSrc);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        RtnMessage rtn = RtnMessageUtils.buildError(ErrorType.NEEDLOGIN);
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("application/json;charset=utf-8");
        servletResponse.getWriter().println(JSON.toJSONString(rtn));
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
            logger.error("WebAuthenticationFilter.onLoginFailure,{}",JSON.toJSONString(rtn));
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
