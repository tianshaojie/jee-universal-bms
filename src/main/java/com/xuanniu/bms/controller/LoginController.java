package com.xuanniu.bms.controller;

import com.xuanniu.framework.common.JsonResult;
import com.xuanniu.framework.common.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tiansj on 15/6/24.
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    @RequestMapping(value = "v1/api0/security/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@RequestParam() String username,
                            @RequestParam() String password,
                            String verifyCode,
                            HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG);
        try {
            if(StringUtils.isBlank(username)) {
                return new JsonResult(ResultCode.ERROR_CODE_701, "请输入用户名");
            }
            if(StringUtils.isBlank(password)) {
                return new JsonResult(ResultCode.ERROR_CODE_702, "请输入密码");
            }

            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(username, password));
            if (subject.isAuthenticated()) {
                jsonResult.setMsg("登录成功");
            } else {
                jsonResult.setCode(ResultCode.ERROR_CODE_703);
                jsonResult.setMsg("用户名或密码错误");
            }
        } catch (AuthenticationException e){
            jsonResult.setCode(ResultCode.ERROR_CODE_703);
            jsonResult.setMsg("用户名或密码错误");
            e.printStackTrace();
        }
        return jsonResult;
    }
}
