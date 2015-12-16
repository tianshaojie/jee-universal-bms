package com.yuzhi.back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuzhi.back.dao.entity.Role;
import com.yuzhi.back.dao.entity.RoleResource;
import com.yuzhi.back.dao.mapper.RoleMapper;
import com.yuzhi.back.dao.mapper.RoleResourceMapper;
import com.yuzhi.back.dao.mapper.UserRoleMapper;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiansj on 15/6/21.
 */
@Component
public class RoleService {
    
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    RoleResourceMapper roleResourceMapper;

    public boolean addRole(Role Role) {
        return roleMapper.insertSelective(Role) == 1;
    }

    public boolean deleteRole(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId) == 1;
    }

    public JsonResult batchDeleteRole(String ids) {
        if(StringUtils.isEmpty(ids)) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        String[] arr = ids.split(",");
        List<Long> delList = new ArrayList<>();
        List<String> cannotDelList = new ArrayList<>();
        for(String id : arr) {
            long roleId = Long.valueOf(id);
            int count = this.getRoleUserCount(roleId);
            if(count > 0) {
                cannotDelList.add(id);
            } else {
                delList.add(Long.valueOf(id));
            }
        }
        if(cannotDelList.size() > 0) {
            String cannotDelIds = StringUtils.join(cannotDelList, ",");
            return new JsonResult(ResultCode.SUCCESS_CODE, "ID为(" + cannotDelIds + ")的角色已绑定操作员，无法删除");
        }
        int row = roleMapper.batchDelete(delList);
        return row > 0 ? JsonResult.success() : JsonResult.error();
    }

    public boolean updateRole(Role Role) {
        return roleMapper.updateByPrimaryKeySelective(Role) == 1;
    }

    public boolean updateRoleStatus(Long id, Integer status) {
        return roleMapper.updateRoleStatus(id, status) == 1;
    }

    public Role getRole(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    public PageInfo getRoleList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> list = roleMapper.getRoleList();
        return new PageInfo(list);
    }

    public List<Role> getUserRoles(Long userId) {
        return roleMapper.getUserRoles(userId);
    }

    public int getRoleUserCount(Long roleId) {
        return userRoleMapper.getRoleUserCount(roleId);
    }

    public List<RoleResource> getResourcesByRoleId(Long roleId) {
        return roleResourceMapper.selectByRoleId(roleId);
    }

    public JsonResult bindResource(Long roleId, String resourceIds) {
        try {
            roleResourceMapper.deleteByRoleId(roleId);
            if(StringUtils.isEmpty(resourceIds)) {
                return JsonResult.success();
            }
        } catch (Exception e) {
            return JsonResult.error();
        }

        String[] ids = resourceIds.split(",");
        List<RoleResource> list = new ArrayList<>();
        for(String id : ids) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(Long.valueOf(id));
            list.add(roleResource);
        }
        int row = roleResourceMapper.batchInsert(list);
        return row > 0   ? JsonResult.success() : JsonResult.error();
    }
}
