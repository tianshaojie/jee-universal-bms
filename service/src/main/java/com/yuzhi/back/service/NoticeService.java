package com.yuzhi.back.service;


import com.github.pagehelper.PageInfo;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.dao.entity.Notice;

/**
 * Created by tiansj on 15/5/28.
 */
public interface NoticeService {

    public JsonResult selectById(Long noticeId);

    public JsonResult insert(Notice record);

    public JsonResult updateById(Notice record);

    public JsonResult deleteById(Long noticeId);

    PageInfo getNoticeList(int pageNum, int pageSize, Notice param);

    public boolean batchDelete(String ids);
}
