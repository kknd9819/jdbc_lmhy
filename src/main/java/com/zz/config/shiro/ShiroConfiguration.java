package com.zz.config.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by X-man on 2017/3/30.
 */

@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/admin/common/main");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/common/unauthorized");
        //拦截器
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        filterChainDefinitionMap.put("/admin/","anon");
        filterChainDefinitionMap.put("/login","authc");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/admin/common/captchaImage","anon");
        filterChainDefinitionMap.put("/order/printReport/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        Map<String,Filter> filters = new HashMap<String,Filter>();
        filters.put("authc",authenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthenticationFilter authenticationFilter(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        return authenticationFilter;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //注入领域
        securityManager.setRealm(authenticationRealm());
        //注入缓存管理器;
        securityManager.setCacheManager(ehCacheManager());
        //注入记住我管理器;
    //    securityManager.setRememberMeManager(rememberMeManager());
        //注入会话管理器
      //  securityManager.setSessionManager(defaultWebSessionManager());
        return securityManager;
    }

//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        hashedCredentialsMatcher.setHashIterations(2);
//        return hashedCredentialsMatcher;
//    }

    @Bean
    public AuthenticationRealm authenticationRealm(){
        AuthenticationRealm authenticationRealm = new AuthenticationRealm();
        //注入凭证适配器
        authenticationRealm.setAuthenticationCacheName("authorization");
     //   authenticationRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authenticationRealm;
    }


    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }

//    @Bean
//    public SimpleCookie rememberMeCookie(){
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
//        simpleCookie.setMaxAge(259200);
//        return simpleCookie;
//    }

//    @Bean
//    public CookieRememberMeManager rememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        return cookieRememberMeManager;
//    }

//    @Bean
//    public DefaultWebSessionManager defaultWebSessionManager(){
//        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
//        defaultWebSessionManager.setGlobalSessionTimeout(600000);
//        defaultWebSessionManager.setDeleteInvalidSessions(true);
//        return defaultWebSessionManager;
//    }

//    @Bean
//    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
//        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
//        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
//        methodInvokingFactoryBean.setArguments(new SecurityManager[0]);
//        return methodInvokingFactoryBean;
//    }

}
