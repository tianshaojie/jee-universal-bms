package com.yuzhi.back.framework.shiro;

import com.google.common.base.Strings;
import com.yuzhi.back.common.Constants;
import com.yuzhi.back.dao.entity.Resource;
import com.yuzhi.back.dao.entity.Role;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.service.ResourceService;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.service.impl.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tiansj on 15/6/23.
 */
public class ShiroJdbcRealm extends JdbcRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @javax.annotation.Resource
    private UserService sysUserService;
    @javax.annotation.Resource
    private ResourceService sysResourceService;
    @javax.annotation.Resource
    private RoleService sysRoleService;
    @javax.annotation.Resource
    private ShiroRedisCacheManager shiroRedisCacheManager;

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = String.valueOf(usernamePasswordToken.getUsername());
        String password = new String(usernamePasswordToken.getPassword());
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }

        AuthorizationInfo info = null;

        if (logger.isTraceEnabled()) {
            logger.trace("Retrieving AuthorizationInfo for principals [" + principals + "]");
        }

        Cache<Object, AuthorizationInfo> cache = getAvailableAuthorizationCache();
        if (cache != null) {
            if (logger.isTraceEnabled()) {
                logger.trace("Attempting to retrieve the AuthorizationInfo from cache.");
            }
            Object key = getAuthorizationCacheKey(principals);
            info = cache.get(key);
            if (logger.isTraceEnabled()) {
                if (info == null) {
                    logger.trace("No AuthorizationInfo found in cache for principals [" + principals + "]");
                } else {
                    logger.trace("AuthorizationInfo found in cache for principals [" + principals + "]");
                }
            }
        }
        if (info == null) {
            // Call template method if the info was not found in a cache
            info = doGetAuthorizationInfo(principals);
            // If the info is not null and the cache has been created, then cache the authorization info.
            if (info != null && cache != null) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Caching authorization info for principals: [" + principals + "].");
                }
                Object key = getAuthorizationCacheKey(principals);
                cache.put(key, info);
            }
        }
        return info;
    }

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        if (!Strings.isNullOrEmpty(username)) {
            User user = sysUserService.getByUsername(username);
            if(user != null) {
                SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();

                List<Role> userRoles = sysRoleService.getUserRoles(user.getId());
                Set<String> roles = new HashSet<>();
                for(Role role : userRoles) {
                    roles.add(role.getName());
                }
                authenticationInfo.setRoles(roles);

                List<Resource> userResources = sysResourceService.getUserPermissions(user.getId());
                Set<String> stringPermissions = new HashSet<>();
                for(Resource resource : userResources) {
                    stringPermissions.add(resource.getUrl());
                }
                authenticationInfo.setStringPermissions(stringPermissions);
                return authenticationInfo;
            }
        }
        return null;
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return principals.toString();
    }


    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    public Cache<Object, AuthorizationInfo> getAvailableAuthorizationCache() {
        return shiroRedisCacheManager.getCache(Constants.SHIRO_REDIS_CACHE);
    }

}
