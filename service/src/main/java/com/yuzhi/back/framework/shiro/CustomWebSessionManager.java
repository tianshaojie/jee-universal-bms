package com.yuzhi.back.framework.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ningcl on 15/9/2.
 */
public class CustomWebSessionManager extends DefaultWebSessionManager {

    protected void onStart(Session session, SessionContext context) {
        super.onStart(session,context);
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
    }

}
