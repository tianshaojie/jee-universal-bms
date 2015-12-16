package com.yuzhi.back.service;

import com.github.pagehelper.PageInfo;
import com.yuzhi.back.dao.entity.User;
import com.yuzhi.back.common.JsonResult;

/**
 * Created by tiansj on 15/6/23.
 */
public interface UserService {

    boolean addUser(User user);

    boolean deleteUser(Long userId);

    boolean updateUser(User user);

    boolean updateUserStatus(Long id, Integer status);

    User getUser(Long userId);

    User getByUsername(String username);

    PageInfo getUserList(int pageNum, int pageSize, User user);

    boolean batchDelUser(String ids);

    JsonResult bindRole(Long userId, String roleIds);

    JsonResult batchBindRole(String userIds, String roleIds);
}
