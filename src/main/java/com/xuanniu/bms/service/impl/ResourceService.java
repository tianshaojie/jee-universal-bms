package com.xuanniu.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanniu.bms.dao.entity.Resource;
import com.xuanniu.bms.dao.mapper.ResourceMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tiansj on 15/6/21.
 */
@Component
public class ResourceService {

    @javax.annotation.Resource
    ResourceMapper resourceMapper;

    public boolean addResource(Resource resource) {
        return resourceMapper.insertSelective(resource) == 1;
    }

    public boolean deleteResource(Long resourceId) {
        return resourceMapper.deleteByPrimaryKey(resourceId) == 1;
    }

    public boolean updateResource(Resource resource) {
        return resourceMapper.updateByPrimaryKeySelective(resource) == 1;
    }

    public Resource getResource(Long resourceId) {
        return resourceMapper.selectByPrimaryKey(resourceId);
    }

    public PageInfo getResourceList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Resource> list = resourceMapper.getResourceList();
        return new PageInfo(list);
    }

    public List<Resource> getUserPermissions(Long userId) {
        return resourceMapper.getUserPermissions(userId);
    }
}
