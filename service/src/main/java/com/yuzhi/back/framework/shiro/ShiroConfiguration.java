package com.yuzhi.back.framework.shiro;

import com.yuzhi.back.common.Constants;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tiansj on 15/6/23.
 */
@Configuration
public class ShiroConfiguration {

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "shiroRealm")
    public ShiroJdbcRealm getShiroJdbcRealm() {
        return new ShiroJdbcRealm();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(getShiroJdbcRealm());
        // webSecurityManager.setCacheManager(getEhCacheManager());
        webSecurityManager.setCacheManager(getCacheManager());
        webSecurityManager.setSessionManager(getSessionManager());
        return webSecurityManager;
    }

    @Bean
    public ShiroRedisCacheManager getCacheManager() {
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
        cacheManager.setKeyPrefix(Constants.SHIRO_REDIS_CACHE);
        return cacheManager;
    }

//    @Bean(name = "shiroEhcacheManager")
//    public EhCacheManager getEhCacheManager() {
//        EhCacheManager em = new EhCacheManager();
//        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
//        return em;
//    }

    @Bean
    public DefaultWebSessionManager getSessionManager() {
        CustomWebSessionManager sessionManager = new CustomWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);            // 设置全局会话超时时间，默认30分钟(1800000)
        sessionManager.setDeleteInvalidSessions(true);              // 是否在会话过期后会调用SessionDAO的delete方法删除会话 默认true
        sessionManager.setSessionValidationInterval(1800000);       // 会话验证器调度时间
        sessionManager.setSessionDAO(getSessionDao());              // 自定义SessionDao
//        sessionManager.setSessionIdCookie(getSessionIdCookie());    // 自定义Cookie
        sessionManager.setSessionValidationSchedulerEnabled(true);  // 定时检查失效的session
        return sessionManager;
    }

    @Bean
    public SessionDAO getSessionDao() {
        ShiroRedisSessionDao sessionDAO = new ShiroRedisSessionDao();
        sessionDAO.setKeyPrefix(Constants.SHIRO_REDIS_SESSION);
        return sessionDAO;
    }

//    @Bean(name = "sessionIdCookie")
//    public Cookie getSessionIdCookie() {
//        SimpleCookie cookie = new SimpleCookie("HUAYUAN-JSESSIONID"); // cookie的name,对应的默认是 JSESSIONID
//        cookie.setHttpOnly(true); // jsessionId的path为 / 用于多个系统共享jsessionId
//        cookie.setPath("/");
//        return cookie;
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/home.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");

        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/v1/api0/image/captcha", "anon"); // 匿名用户可访问
        filterChainDefinitionMap.put("/view/**", "perms");                  // Url权限过滤
        filterChainDefinitionMap.put("/v1/api0/security/login", "anon");    // 登陆用户
        filterChainDefinitionMap.put("/v1/api0/security/logout", "anon");    // 登陆用户
        filterChainDefinitionMap.put("/v1/api0/user*//*", "user");       // 登陆用户
        //>>>>>>
        filterChainDefinitionMap.put("/v1/api0/orgcate*//**//**//**//*", "user");       // 登陆用户
        filterChainDefinitionMap.put("/v1/api0/resource*//**//**//**//*", "user");       // 登陆用户
        filterChainDefinitionMap.put("/v1/api0/role*//**//**//**//*", "user");
        //<<<<<<
        //filterChainDefinitionMap.put("/v1/api0/**/**", "user,perms");       // 登陆用户
        filterChainDefinitionMap.put("/v1/api0/**/**", "user");       // 登陆用户
        filterChainDefinitionMap.put("/home.html", "authc");                // 登陆用户
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("anon", new AnonymousFilter());
        filters.put("user", new UserFilter());
        filters.put("authc", new FormAuthenticationFilter());
        filters.put("perms", new ShiroPermissionsFilter()); // 自定义权限过滤
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }
}
