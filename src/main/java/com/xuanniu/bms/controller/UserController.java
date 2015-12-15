package com.xuanniu.bms.controller;

import com.github.pagehelper.PageInfo;
import com.xuanniu.bms.dao.entity.User;
import com.xuanniu.bms.service.UserService;
import com.xuanniu.framework.common.DataGrid;
import com.xuanniu.framework.common.Page;
import com.xuanniu.framework.common.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by xuejt on 2015/4/3.
 */
@Controller
@RequestMapping(value = "/")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping(value = "v1/api0/user/list")
    @ResponseBody
    public DataGrid<User> userList(Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 10 : pageSize;
        PageInfo pageInfo = userService.getUserList(pageIndex, pageSize);
        PageList<User> pageList = new PageList<>();
        pageList.setList(pageInfo.getList());
        Page page = new Page(pageIndex, pageSize, (int)pageInfo.getTotal());
        pageList.setPage(page);
        return new DataGrid(pageList, 200);
    }

}
