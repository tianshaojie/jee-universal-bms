package com.yuzhi.back.service.impl;

import com.yuzhi.back.dao.entity.Resource;
import com.yuzhi.back.dao.mapper.ResourceMapper;
import com.yuzhi.back.dao.mapper.RoleResourceMapper;
import com.yuzhi.back.service.ResourceService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tiansj on 15/6/21.
 */
@Component
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource
    ResourceMapper resourceMapper;
    @javax.annotation.Resource
    RoleResourceMapper roleResourceMapper;

    public boolean addResource(Resource resource) {
        int row = resourceMapper.insertSelective(resource);
        if(row > 0) {
            Resource r = new Resource();
            r.setId(resource.getId());
            r.setSort(resource.getId());
            resourceMapper.updateByPrimaryKeySelective(r);
        }
        return row > 0;
    }

    public boolean deleteResource(Long resourceId) {
        int row = resourceMapper.deleteByPrimaryKey(resourceId);
        if(row > 0) {
            roleResourceMapper.deleteByResourceId(resourceId);
        }
        return row > 0;
    }

    public boolean updateResource(Resource resource) {
        return resourceMapper.updateByPrimaryKeySelective(resource) == 1;
    }

    public Resource getResource(Long resourceId) {
        return resourceMapper.selectByPrimaryKey(resourceId);
    }

    public List<Resource> getResourceList() {
        return resourceMapper.getResourceList();
    }

    public List<Resource> getUserPermissions(Long userId) {
        return resourceMapper.getUserPermissions(userId);
    }

    public int getChildrenCount(Long pid) {
        return resourceMapper.getChildrenCount(pid);
    }

    public int batchUpdateSort(List<Resource> list) {
        return resourceMapper.batchUpdateSort(list);
    }
}
