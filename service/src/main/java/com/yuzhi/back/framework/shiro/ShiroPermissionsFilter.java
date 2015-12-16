package com.yuzhi.back.framework.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by tiansj on 15/8/12.
 */
public class ShiroPermissionsFilter extends AuthorizationFilter {

    public ShiroPermissionsFilter() {
    }

    /**
     * 对访问的Url和当前用户进行权限认证
     * @param request       封装了HttpServletRequest
     * @param response      封装了HttpServletRequest
     * @param mappedValue   配置中传递的数据，这里不需要
     * @return
     */
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String url = ((ShiroHttpServletRequest) request).getRequestURI();
        Subject subject = this.getSubject(request, response);
        return subject.isPermitted(url);
    }
}