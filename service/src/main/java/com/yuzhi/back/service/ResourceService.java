package com.yuzhi.back.service;

import com.yuzhi.back.dao.entity.Resource;

import java.util.List;

/**
 * Created by tiansj on 15/6/21.
 */
public interface ResourceService {

    boolean addResource(Resource resource);

    boolean deleteResource(Long resourceId);

    boolean updateResource(Resource resource);

    Resource getResource(Long resourceId);

    List<Resource> getResourceList();

    List<Resource> getUserPermissions(Long userId);

    int getChildrenCount(Long pid);

    int batchUpdateSort(List<Resource> list);
}
