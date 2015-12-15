package com.xuanniu.framework.config;

import com.google.common.base.Strings;
import com.xuanniu.bms.dao.entity.Resource;
import com.xuanniu.bms.dao.entity.Role;
import com.xuanniu.bms.dao.entity.User;
import com.xuanniu.bms.service.UserService;
import com.xuanniu.bms.service.impl.ResourceService;
import com.xuanniu.bms.service.impl.RoleService;
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
        User user = userService.findByUserName(username);
        AuthenticationInfo authenticationInfo = null;
        if (null != user) {
            String password = new String(usernamePasswordToken.getPassword());
            if (password.equals(user.getPassword())) {
                authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            }
        }
        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
        if (!Strings.isNullOrEmpty(username)) {
            User user = userService.findByUserName(username);
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
