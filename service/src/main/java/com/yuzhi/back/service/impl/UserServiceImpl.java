package com.yuzhi.back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.dao.entity.UserRole;
import com.yuzhi.back.dao.mapper.UserMapper;
import com.yuzhi.back.dao.mapper.UserRoleMapper;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.utils.BeanMapUtils;
import com.yuzhi.back.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiansj on 15/6/21.
 */
@Component
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    public boolean addUser(User user) {
        return userMapper.insertSelective(user) == 1;
    }

    public boolean deleteUser(Long userId) {
        return userMapper.deleteByPrimaryKey(userId) == 1;
    }

    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    public boolean updateUserStatus(Long id, Integer status) {
        return userMapper.updateUserStatus(id, status) == 1;
    }

    public User getUser(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    public PageInfo getUserList(int pageNum, int pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.getUserList(BeanMapUtils.toMap(user));
        return new PageInfo(list);
    }

    @Override
    public boolean batchDelUser(String ids) {
        List<Long> list = new ArrayList<>();
        String[] arr = ids.split(",");
        for(String id : arr) {
            long userId = Long.valueOf(id);
            if(userId >= 10) {  // 10以下id为初始化
                list.add(Long.valueOf(id));
            }
        }
        return userMapper.batchDelUser(list) > 0;
    }

    public JsonResult bindRole(Long userId, String roleIds) {
        try {
            userRoleMapper.deleteByUserId(userId);
            if(StringUtils.isEmpty(roleIds)) {
                return JsonResult.success();
            }
        } catch (Exception e) {
            return JsonResult.error();
        }
        String[] ids = roleIds.split(",");
        List<UserRole> list = new ArrayList<>();
        for(String id : ids) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(Long.valueOf(id));
            list.add(userRole);
        }
        int row = userRoleMapper.batchInsert(list);
        return row > 0 ? JsonResult.success() : JsonResult.error();
    }

    public JsonResult batchBindRole(String userIds, String roleIds) {
        if(StringUtils.isEmpty(userIds)) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        List<String> errorIds = new ArrayList<>();
        String[] ids = userIds.split(",");
        for(String id : ids) {
            JsonResult result = bindRole(Long.valueOf(id), roleIds);
            if(result.getCode() != ResultCode.SUCCESS_CODE) {
                errorIds.add(id);
            }
        }
        if(errorIds.size() == 0) {
            return JsonResult.success();
        }
        if(errorIds.size() < ids.length) {
            String str = StringUtils.join(errorIds, ",");
            return new JsonResult(ResultCode.SUCCESS_CODE, "部分用户绑定角色失败，ID=(" +str+ ")");
        } else {
            return new JsonResult(ResultCode.ERROR_CODE, "绑定用户角色失败，请重试");
        }
    }
}
