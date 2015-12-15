package com.xuanniu.bms.service;

import com.github.pagehelper.PageInfo;
import com.xuanniu.bms.dao.entity.User;

/**
 * Created by tiansj on 15/6/23.
 */
public interface UserService {

    boolean addUser(User user);

    boolean deleteUser(Long userId);

    boolean updateUser(User user);

    User getUser(Long userId);

    User findByUserName(String username);

    PageInfo getUserList(int pageNum, int pageSize);

}
