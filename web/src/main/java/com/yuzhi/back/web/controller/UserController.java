package com.yuzhi.back.web.controller;

import com.github.pagehelper.PageInfo;
import com.yuzhi.back.common.Constants;
import com.yuzhi.back.dao.entity.Role;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.service.impl.RoleService;
import com.yuzhi.back.common.DataGrid;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.utils.StringUtils;
import com.yuzhi.back.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xuejt on 2015/4/3.
 */
@Controller
@RequestMapping(value = "/")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

    @RequestMapping(value = "v1/api0/user/list")
    @ResponseBody
    public DataGrid<User> userList(Integer page, Integer rows, User user) {
        page = page == null ? 1 : page;
        rows = rows == null ? 10 : rows;
        PageInfo pageInfo = userService.getUserList(page, rows, user);
        return new DataGrid(pageInfo);
    }


    @RequestMapping(value = "v1/api0/user/add")
    @ResponseBody
    public JsonResult add(User user) {
        if(user == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        if(StringUtils.isBlank(user.getUsername())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户名不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "密码不能为空");
        }

        user.setUsername(user.getUsername().trim());

        User u = userService.getByUsername(user.getUsername());
        if(u != null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户名重复，请重新输入");
        }

        String passowrd = user.getPassword().trim();
        String salt = Utils.generateSalt();
        user.setSalt(salt);
        user.setPassword(Utils.encryptPassword(salt, passowrd));

        boolean isSuccess = userService.addUser(user);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/user/edit")
    @ResponseBody
    public JsonResult edit(User user) {
        if(user == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        if(StringUtils.isBlank(user.getUsername())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "用户名不能为空");
        }
        user.setUsername(user.getUsername().trim());
        boolean isSuccess = userService.updateUser(user);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/user/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(String ids) {
        if(StringUtils.isEmpty(ids)) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }

        boolean isSuccess = userService.batchDelUser(ids);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/user/enable", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult enable(Long id) {
        if(id == null || id <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        boolean isSuccess = userService.updateUserStatus(id, Constants.USER_STATUS_ENABLE);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/user/disable", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult disable(Long id) {
        if(id == null || id <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        boolean isSuccess = userService.updateUserStatus(id, Constants.USER_STATUS_DISABLE);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/user/password/reset", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult resetPassword(Long id) {
        if(id == null || id <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }

        User user = new User();
        user.setId(id);
        String salt = Utils.generateSalt();
        user.setSalt(salt);
        user.setPassword(Utils.encryptPassword(salt, "111111"));

        boolean isSuccess = userService.updateUser(user);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }


    @RequestMapping(value = "v1/api0/user/bindRole", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult bindRole(Long userId, String roleIds) {
        if(userId == null || userId <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        return userService.bindRole(userId, roleIds);
    }

    /*@RequestMapping(value = "v1/api0/user/batchBindRole", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult batchBindRole(String userIds, String roleIds) {
        if(StringUtils.isEmpty(userIds)) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        return userService.batchBindRole(userIds, roleIds);
    }*/

    @RequestMapping(value = "v1/api0/user/roles", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult userRoles(Long userId) {
        if(userId == null || userId <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        List<Role> list = roleService.getUserRoles(userId);
        return new JsonResult<>(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG, list);
    }
}
