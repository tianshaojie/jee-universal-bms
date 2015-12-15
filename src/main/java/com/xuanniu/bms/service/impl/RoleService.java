package com.xuanniu.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanniu.bms.dao.entity.Role;
import com.xuanniu.bms.dao.entity.RoleResource;
import com.xuanniu.bms.dao.entity.UserRole;
import com.xuanniu.bms.dao.mapper.RoleMapper;
import com.xuanniu.bms.dao.mapper.RoleResourceMapper;
import com.xuanniu.bms.dao.mapper.UserRoleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    public boolean updateRole(Role Role) {
        return roleMapper.updateByPrimaryKeySelective(Role) == 1;
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

    public boolean addUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRoleMapper.insert(userRole) == 1;
    }

    public boolean deleteUserRole(Long id) {
        return userRoleMapper.deleteByPrimaryKey(id) == 1;
    }

    public boolean addRoleResource(Long resourceId, Long roleId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setResourceId(resourceId);
        roleResource.setRoleId(roleId);
        return roleResourceMapper.insert(roleResource) == 1;
    }

    public boolean deleteRoleResource(Long id) {
        return roleResourceMapper.deleteByPrimaryKey(id) == 1;
    }
}
