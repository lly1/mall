package com.mall.config;

import com.mall.filter.WxAuthenticationFilter;
import com.mall.shiro.realm.WebShiroRealm;
import com.mall.shiro.realm.WxShiroRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lly
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 没有登陆的用户只能访问登陆页面
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/auth/index");
        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("user", new WxAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        // 权限控制map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //可以匿名使用
        filterChainDefinitionMap.put("/static/**","anon");
        //让小程序通过
        filterChainDefinitionMap.put("/wxAuth/login", "anon");
        filterChainDefinitionMap.put("/auth/login", "anon");
        filterChainDefinitionMap.put("/auth/login/main","anon");
        filterChainDefinitionMap.put("/auth/kickout", "anon");
        filterChainDefinitionMap.put("/auth/logout","authc");
        //顺序不能错
        filterChainDefinitionMap.put("/api/**", "user");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public WxAuthenticationFilter WxAuthenticationFilter() {
        return new WxAuthenticationFilter();
    }

    @Bean
    public WebShiroRealm WebShiroRealm(){
        return new WebShiroRealm();
    }

    @Bean
    public WxShiroRealm WxShiroRealm(){
        return new WxShiroRealm();
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        //添加多个Realm
        realms.add(WebShiroRealm());
        realms.add(WxShiroRealm());
        securityManager.setAuthenticator(modularRealmAuthenticator());
        securityManager.setRealms(realms);
        return securityManager;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm 认证
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        ModularRealmAuthenticator  modularRealmAuthenticator = new ModularRealmAuthenticator ();
        //至少一个realm认证成功
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
    private RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        // 配置缓存过期时间
        redisManager.setExpire(1800);
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * AOP式方法级权限检查
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}