package com.yuzhi.back.web.controller;

import com.alibaba.fastjson.JSON;
import com.yuzhi.back.dao.entity.Resource;
import com.yuzhi.back.service.ResourceService;
import com.yuzhi.back.service.UserService;
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
public class ResourceController {

    @javax.annotation.Resource
    UserService userService;
    @javax.annotation.Resource
    ResourceService resourceService;

    @RequestMapping(value = "v1/api0/resource/list")
    @ResponseBody
    public List<Tree> resourceList() {
        List<Resource> resourceList = resourceService.getResourceList();
        return wrapTree(resourceList, 0);
    }

    @RequestMapping(value = "v1/api0/resource/listWithRoot")
    @ResponseBody
    public List<Tree> resourceListWithRoot() {
        List<Resource> resourceList = resourceService.getResourceList();
        List<Tree> treeList = new ArrayList<>();
        Tree root = new Tree(0L, -1L, "根节点", "icon-application_side_tree");
        root.setChildren(wrapTree(resourceList, 0));
        treeList.add(root);
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

    @RequestMapping(value = "v1/api0/resource/add")
    @ResponseBody
    public JsonResult add(Resource resource) {
        if(resource == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        if(StringUtils.isBlank(resource.getName())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "菜单名称不能为空");
        }
        if(resource.getPid() == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "请选择父级菜单");
        }

        boolean isSuccess = resourceService.addResource(resource);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/resource/edit")
    @ResponseBody
    public JsonResult edit(Resource resource) {
        if(resource == null || resource.getId() == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        if(StringUtils.isBlank(resource.getName())) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "菜单名称不能为空");
        }
        if(resource.getPid() == null) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "请选择父级菜单");
        }

        boolean isSuccess = resourceService.updateResource(resource);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "v1/api0/resource/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(Long id) {
        if(id == null || id <= 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }

        int count = resourceService.getChildrenCount(id);
        if(count > 0) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, "请先删除所有子节点");
        }

        boolean isSuccess = resourceService.deleteResource(id);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    private static long sort = 1;
    @RequestMapping(value = "v1/api0/resource/sort")
    @ResponseBody
    public JsonResult edit(String treeStr) {
        if(StringUtils.isEmpty(treeStr)) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }
        Tree tree = JSON.parseObject(treeStr, Tree.class);
        List<Resource> list = new ArrayList<>();
        sort = 1;
        tree2List(tree.getChildren(), list, 0L);

        JsonResult result = JsonResult.success();
        try {
            resourceService.batchUpdateSort(list);
        } catch (Exception e) {
            result = JsonResult.error();
        } finally {
            return result;
        }
    }

    public void tree2List(List<Tree> trees, List<Resource> list, Long pid) {
        for (Tree tree : trees) {
            Resource resource = new Resource();
            resource.setId(tree.getId());
            resource.setSort(sort++);
            resource.setPid(pid);
            list.add(resource);
            if(tree.getChildren() != null && tree.getChildren().size() > 0) {
                tree2List(tree.getChildren(), list, tree.getId());
            }
        }
    }
}
