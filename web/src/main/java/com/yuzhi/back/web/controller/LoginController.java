package com.yuzhi.back.web.controller;

import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.utils.Utils;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tiansj on 15/6/24.
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Resource
    UserService userService;

    @RequestMapping(value = "v1/api0/security/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@RequestParam() String username,
                            @RequestParam() String password,
                            String verifyCode,
                            HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG);
        try {
            if(StringUtils.isBlank(username)) {
                return new JsonResult(ResultCode.PARAM_ERROR_CODE, "请输入用户名");
            }
            if(StringUtils.isBlank(password)) {
                return new JsonResult(ResultCode.PARAM_ERROR_CODE, "请输入密码");
            }

            User user = userService.getByUsername(username);
            if(user == null) {
                return new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户不存在");
            }
            if(user.getStatus() != 1) {
                return new JsonResult(ResultCode.PARAM_ERROR_CODE, "该用户已被禁用");
            }
            String salt = user.getSalt();
            password = Utils.encryptPassword(salt, password);
            if (!password.equals(user.getPassword())) {
                return new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户名或密码错误");
            }
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(username, password));
            if (subject.isAuthenticated()) {
                jsonResult.setMsg("登录成功");
            } else {
                return new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户名或密码错误");
            }
        } catch (AuthenticationException e){
            jsonResult =  new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户名或密码错误");
            e.printStackTrace();
        }
        return jsonResult;
    }
}
