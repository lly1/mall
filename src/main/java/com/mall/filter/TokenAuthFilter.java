package com.mall.filter;

import com.mall.utils.cache.RedisUtils;
import com.mall.wxshop.config.WxConfig;
import com.mall.wxshop.util.AppContext;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author lly
 * 自定义拦截器
 */
@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        //获取请求头部分的Authorization
        String authHeader = request.getHeader(WxConfig.header);
        //如果请求路径为微信通知后台支付结果则不需要token（之后会在具体的controller中，对双方签名进行验证防钓鱼）
        String url = request.getRequestURI();
        //只拦截微信请求
        if(url.contains("api")){
            if (url.contains("login") || url.contains("test")) {
                chain.doFilter(request, response);
                return;
            }

            if (null == authHeader || !authHeader.startsWith("mall")) {
                throw new RuntimeException("非法访问用户");
            }
            final String token = authHeader.substring(WxConfig.tokenHead.length());
            String wxSessionObj = (String)redisUtils.get(token.trim());
            if (StringUtils.isEmpty(wxSessionObj)) {
                throw new RuntimeException("用户身份已过期");
            }
            else {
                //更新缓存时间
                redisUtils.set(token,redisUtils.get(token),WxConfig.EXPIRES);
            }

            // 设置当前登录用户的openid
            try (AppContext appContext = new AppContext(wxSessionObj.substring(wxSessionObj.indexOf("#") + 1))) {
                chain.doFilter(request, response);
            }
        }
        else {
            chain.doFilter(request,response);
        }
    }
}