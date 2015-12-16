package com.yuzhi.back.web.controller;

import com.yuzhi.back.dao.entity.Resource;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.service.ResourceService;
import com.yuzhi.back.service.UserService;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.common.Tree;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by xuejt on 2015/4/3.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @javax.annotation.Resource
    UserService userService;
    @javax.annotation.Resource
    ResourceService resourceService;

    @RequestMapping(value = "v1/api0/user/resource")
    @ResponseBody
    public JsonResult userResource() {
        Subject currentUser = SecurityUtils.getSubject();//获取当前用户
        if(currentUser != null && currentUser.getPrincipals() != null) {
            String username = currentUser.getPrincipals().toString();
            User user = userService.getByUsername(username);
            if(user != null) {
                List<Resource> resourceList = resourceService.getUserPermissions(user.getId());
                List<Tree> treeList = wrapTree(resourceList, 0);
                return new JsonResult(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG, treeList);
            }
        }
        return new JsonResult(ResultCode.TOKEN_ERROR_CODE, ResultCode.TOKEN_ERROR_MSG);
    }

    public List<Tree> wrapTree(List<Resource> resourceList, long pid) {
        List<Tree> treeList = new ArrayList<>();
        for (Resource resource : resourceList) {
            Tree tree = new Tree(resource.getId(), resource.getPid(), resource.getName(), resource.getIcon(), resource.getSort());
            if (pid == resource.getPid()) {
                List<Tree> children = wrapTree(resourceList, resource.getId());
                Collections.sort(children, (arg0, arg1) -> (int)(arg0.getSort() - arg1.getSort()));
                Map<String, String> attributes = new HashMap<>();
                attributes.put("url", resource.getUrl());
                tree.setAttributes(attributes);
                tree.setChildren(children);
                treeList.add(tree);
            }
        }
        return treeList;
    }

}
