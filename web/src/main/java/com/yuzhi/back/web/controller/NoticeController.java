package com.yuzhi.back.web.controller;

import com.github.pagehelper.PageInfo;
import com.yuzhi.back.common.DataGrid;
import com.yuzhi.back.common.JsonResult;
import com.yuzhi.back.common.ResultCode;
import com.yuzhi.back.dao.entity.Notice;
import com.yuzhi.back.service.NoticeService;
import com.yuzhi.back.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by tiansj on 15/5/29.
 */
@Controller
@RequestMapping(value = "/v1/api0/notice/")
public class NoticeController {

    @Resource
    NoticeService noticeService;

    @RequestMapping(value="list")
    @ResponseBody
    public DataGrid search(Integer page, Integer rows, String title, Integer category) {
        Notice param = new Notice();
        if(category != null && category > 0) {
            param.setCategory(category);
        }
        if(!StringUtils.isBlank(title)) {
            param.setTitle(title);
        }
        page = page == null ? 1 : page;
        rows = rows == null ? 10 : rows;
        PageInfo pageList = noticeService.getNoticeList(page, rows, param);
        return new DataGrid(pageList, ResultCode.SUCCESS_CODE);
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(Notice notice) {
        return noticeService.insert(notice);
    }

    @RequestMapping(value = "publish", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult publish(Long id) {
        Notice notice = new Notice();
        notice.setNoticeId(id);
        notice.setPublishTime(new Date());
        notice.setPublishStatus(2);
        return noticeService.updateById(notice);
    }

    @RequestMapping(value = "cancelPublish", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cancelPublish(Long id) {
        Notice notice = new Notice();
        notice.setNoticeId(id);
        notice.setPublishStatus(1);
        return noticeService.updateById(notice);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(String ids) {
        if(StringUtils.isEmpty(ids)) {
            return new JsonResult(ResultCode.PARAM_ERROR_CODE, ResultCode.PARAM_ERROR_MSG);
        }

        boolean isSuccess = noticeService.batchDelete(ids);
        return isSuccess ? JsonResult.success() : JsonResult.error();
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult edit(Notice notice) {
        return noticeService.updateById(notice);
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult get(Long id) {
        return noticeService.selectById(id);
    }
}
