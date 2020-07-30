package com.mall.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangl on 2017/11/30.
 *
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
//        LOGGER.info("当前请求路径.."+httpServletRequest.getRequestURI());
//        System.out.println("当前请求路径.."+httpServletRequest.getRequestURI());
//        if (siteService == null || userService == null) {//解决service为null无法注入问题
//            System.out.println("siteService is null!!!");
//            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
//            siteService = (SiteService) factory.getBean("siteService");
//            userService = (UserService) factory.getBean("userService");

    //}
        return true;
//        httpServletRequest.setAttribute("site",siteService.getCurrentSite());
//        user user = userService.findUserById(MySysUser.id());
//        if(user != null){
//            httpServletRequest.setAttribute("currentUser",user);
//            return true;
//        }
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
