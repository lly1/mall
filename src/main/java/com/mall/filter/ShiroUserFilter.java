package com.mall.filter;

import com.alibaba.fastjson.JSON;
import com.mall.common.RtnMessage;
import com.mall.constant.ErrorType;
import com.mall.utils.RtnMessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lly
 * 处理鉴权
 */
public class ShiroUserFilter extends UserFilter {
    private static Logger logger = LoggerFactory.getLogger(ShiroUserFilter.class);
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return responseAccessDenied(request,response);
    }

    private boolean responseAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
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
        logger.error("ShiroUserFilter.responseAccessDenied,{}", JSON.toJSONString(rtn));
        return false;
    }
}
