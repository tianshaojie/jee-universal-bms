package com.yuzhi.back.web.controller;

import com.github.pagehelper.PageInfo;
import com.yuzhi.back.common.Constants;
import com.yuzhi.back.dao.entity.Resource;
import com.yuzhi.back.dao.entity.Role;
import com.yuzhi.back.dao.entity.RoleResource;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.service.ResourceService;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.service.impl.RoleService;
import com.yuzhi.back.common.DataGrid;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.common.Tree;
import com.yuzhi.back.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuejt on 2015/4/3.
 */
@Controller
@RequestMapping(value = "/")
public class RoleController {

    @javax.annotation.Resource
    UserService userService;
    @javax.annotation.Resource
    RoleService roleService;
    @javax.annotation.Resource
    ResourceService resourceService;

    @RequestMapping(value = "v1/api0/role/list")
    @ResponseBody
    public DataGrid<User> userList(Integer page, Integer rows, Role role) {
        page = page == null ? 1 : page;
        rows = rows == null ? 10 : rows;
        PageInfo pageInfo = roleService.getRoleList(page, rows);
        return new DataGrid(pageInfo);
    }

    @RequestMapping(value = "v1/api0/role/add")
    @ResponseBody
    public JsonResult add(Role role) {
        if(role == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        if(StringUtils.isBlank(role.getName())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "角色名称不能为空");
        }

        boolean isSuccess = roleService.addRole(role);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/role/edit")
    @ResponseBody
    public JsonResult edit(Role role) {
        if(role == null || role.getId() == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        if(StringUtils.isBlank(role.getName())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "角色名称不能为空");
        }

        boolean isSuccess = roleService.updateRole(role);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/role/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(String ids) {
        return roleService.batchDeleteRole(ids);
    }

    @RequestMapping(value = "v1/api0/role/resource")
    @ResponseBody
    public List<Tree> resourceList(Long roleId) {
        List<RoleResource> roleResourceList = roleService.getResourcesByRoleId(roleId);
        List<Resource> resourceList = resourceService.getResourceList();
        List<Tree> treeList = wrapTree(resourceList, 0);
        treeNoteChecked(treeList, roleResourceList);
        return treeList;
    }

    private List<Tree> wrapTree(List<Resource> resourceList, long pid) {
        List<Tree> treeList = new ArrayList<>();
        if(resourceList == null || resourceList.size() == 0) {
            return treeList;
        }
        for (Resource resource : resourceList) {
            if (pid == resource.getPid()) {
                Tree tree = new Tree(resource.getId(), resource.getPid(), resource.getName(), resource.getIcon(), resource.getSort(), resource.getUrl());
                List<Tree> children = wrapTree(resourceList, resource.getId());
                Collections.sort(children, (arg0, arg1) -> (int)(arg0.getSort() - arg1.getSort()));
                tree.setChildren(children);
                treeList.add(tree);
            }
        }
        Collections.sort(treeList, (arg0, arg1) -> (int)(arg0.getSort() - arg1.getSort()));
        return treeList;
    }

    private void treeNoteChecked(List<Tree> treeList, List<RoleResource> roleResourceList) {
        if(roleResourceList == null && roleResourceList.size() == 0) {
            return;
        }
        for(Tree tree : treeList) {
            List<Tree> children = tree.getChildren();
            if(children == null || children.size() == 0) {
                for(RoleResource roleResource : roleResourceList) {
                    if(tree.getId() == roleResource.getResourceId()) {
                        tree.setChecked(true);
                        break;
                    }
                }
            } else {
                treeNoteChecked(children, roleResourceList);
            }
        }
    }

    @RequestMapping(value = "v1/api0/role/bindResource", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult bindResource(Long roleId, String resourceIds) {
        if(roleId == null || roleId <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        return roleService.bindResource(roleId, resourceIds);
    }

    @RequestMapping(value = "v1/api0/role/enable", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult enable(Long id) {
        if(id == null || id <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        boolean isSuccess = roleService.updateRoleStatus(id, Constants.ROLE_STATUS_ENABLE);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/role/disable", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult disable(Long id) {
        if(id == null || id <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        boolean isSuccess = roleService.updateRoleStatus(id, Constants.ROLE_STATUS_DISABLE);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }
}
