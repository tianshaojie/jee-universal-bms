package com.yuzhi.back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.dao.entity.Notice;
import com.yuzhi.back.dao.mapper.NoticeMapper;
import com.yuzhi.back.service.NoticeService;
import com.yuzhi.back.utils.BeanMapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiansj on 15/5/28.
 */
@Component
public class NoticeServiceImpl implements NoticeService {

    @Resource
    NoticeMapper noticeMapper;

    @Override
    public JsonResult<Notice> selectById(Long noticeId) {
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        if(notice == null) {
            return JsonResult.error();
        }
        JsonResult<Notice> jsonResult = JsonResult.success();
        jsonResult.setBody(notice);
        return jsonResult;
    }

    @Override
    public JsonResult insert(Notice record) {
        int row = noticeMapper.insertSelective(record);
        if(row == 1) {
            return JsonResult.success();
        } else {
            return JsonResult.error();
        }
    }

    @Override
    public JsonResult updateById(Notice record) {
        int row = noticeMapper.updateByPrimaryKeySelective(record);
        if (row == 1) {
            return JsonResult.success();
        } else {
            return JsonResult.error();
        }
    }

    @Override
    public JsonResult deleteById(Long noticeId) {
        int row = noticeMapper.deleteByPrimaryKey(noticeId);
        if(row == 1) {
            return JsonResult.success();
        } else {
            return JsonResult.error();
        }
    }

    @Override
    public PageInfo getNoticeList(int pageNum, int pageSize, Notice param) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> noticeList = noticeMapper.getNoticeList(BeanMapUtils.toMap(param));
        return new PageInfo(noticeList);
    }

    public boolean batchDelete(String ids) {
        List<Long> list = new ArrayList<>();
        String[] arr = ids.split(",");
        for(String id : arr) {
            list.add(Long.valueOf(id));
        }
        return noticeMapper.batchDelNotice(list) > 0;
    }
}
