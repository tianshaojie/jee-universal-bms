package com.xuanniu.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanniu.bms.dao.entity.User;
import com.xuanniu.bms.dao.mapper.UserMapper;
import com.xuanniu.bms.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tiansj on 15/6/21.
 */
@Component
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    public boolean addUser(User user) {
        return userMapper.insertSelective(user) == 1;
    }

    public boolean deleteUser(Long userId) {
        return userMapper.deleteByPrimaryKey(userId) == 1;
    }

    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    public User getUser(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    public PageInfo getUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.getUserList();
        return new PageInfo(list);
    }
}
