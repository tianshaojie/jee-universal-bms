package com.yuzhi.back.web.controller;

import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tiansj on 15/7/14.
 */
@Controller
@RequestMapping(value = "/")
public class LogoutController {

    @RequestMapping(value = "logout")
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser != null) {
            currentUser.logout();
        }
        return "redirect:/login.html";
    }

    @RequestMapping(value = "v1/api0/security/logout")
    @ResponseBody
    public JsonResult logoutAjax() {
        JsonResult jsonResult = new JsonResult(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG);
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser != null) {
            currentUser.logout();
        }
        return jsonResult;
    }
}
