package com.yuzhi.back.dao.mapper;

import com.yuzhi.back.dao.entity.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    @Delete({
        "delete from notice",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long noticeId);

    @Insert({
        "insert into notice (notice_id, title, ",
        "source, is_use_link, ",
        "link, publish_time, ",
        "publish_status, create_time, ",
        "category, content)",
        "values (#{noticeId,jdbcType=BIGINT}, #{title,jdbcType=VARCHAR}, ",
        "#{source,jdbcType=VARCHAR}, #{isUseLink,jdbcType=TINYINT}, ",
        "#{link,jdbcType=VARCHAR}, #{publishTime,jdbcType=TIMESTAMP}, ",
        "#{publishStatus,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{category,jdbcType=TINYINT}, #{content,jdbcType=LONGVARCHAR})"
    })
    int insert(Notice record);

    int insertSelective(Notice record);

    @Select({
        "select",
        "notice_id, title, source, is_use_link, link, publish_time, publish_status, create_time, ",
        "category, content",
        "from notice",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    @ResultMap("ResultMapWithBLOBs")
    Notice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(Notice record);

    @Update({
        "update notice",
        "set title = #{title,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=VARCHAR},",
          "is_use_link = #{isUseLink,jdbcType=TINYINT},",
          "link = #{link,jdbcType=VARCHAR},",
          "publish_time = #{publishTime,jdbcType=TIMESTAMP},",
          "publish_status = #{publishStatus,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "category = #{category,jdbcType=TINYINT},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Notice record);

    @Update({
        "update notice",
        "set title = #{title,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=VARCHAR},",
          "is_use_link = #{isUseLink,jdbcType=TINYINT},",
          "link = #{link,jdbcType=VARCHAR},",
          "publish_time = #{publishTime,jdbcType=TIMESTAMP},",
          "publish_status = #{publishStatus,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "category = #{category,jdbcType=TINYINT}",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Notice record);

    /********************以下为自定义*******************/

    List<Notice> getNoticeList(Map map);

    int getNoticeListTotal(Map map);

    int batchDelNotice(List<Long> ids);

}