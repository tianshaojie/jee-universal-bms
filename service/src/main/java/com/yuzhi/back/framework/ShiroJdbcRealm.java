package com.yuzhi.back.framework;

import com.google.common.base.Strings;
import com.yuzhi.back.dao.entity.Resource;
import com.yuzhi.back.dao.entity.Role;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.service.ResourceService;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.service.impl.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tiansj on 15/6/23.
 */
public class ShiroJdbcRealm extends JdbcRealm {

    @javax.annotation.Resource
    private UserService userService;
    @javax.annotation.Resource
    private RoleService roleService;
    @javax.annotation.Resource
    private ResourceService resourceService;

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = String.valueOf(usernamePasswordToken.getUsername());
        String password = new String(usernamePasswordToken.getPassword());
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());;
        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        if (!Strings.isNullOrEmpty(username)) {
            User user = userService.getByUsername(username);
            if(user != null) {
                SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();

                List<Role> userRoles = roleService.getUserRoles(user.getId());
                Set<String> roles = new HashSet<>();
                for(Role role : userRoles) {
                    role.setName(role.getName());
                }
                authenticationInfo.setRoles(roles);

                List<Resource> userResources = resourceService.getUserPermissions(user.getId());
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

}
