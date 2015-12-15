package com.xuanniu.bms.dao.mapper;

import com.xuanniu.bms.dao.entity.Resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ResourceMapper {
    @Delete({
        "delete from sys_resource",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_resource (id, pid, name, ",
        "url, icon, status, ",
        "sort, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=BIGINT}, #{pid,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{url,jdbcType=VARCHAR}, #{icon,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
        "#{sort,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Resource record);

    int insertSelective(Resource record);

    @Select({
        "select",
        "id, pid, name, url, icon, status, sort, create_time, update_time",
        "from sys_resource",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    @Update({
        "update sys_resource",
        "set pid = #{pid,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "icon = #{icon,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "sort = #{sort,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Resource record);

    @Select({
            "select",
            "id, pid, name, url, icon, status, sort, create_time, update_time",
            "from sys_resource"
    })
    @ResultMap("BaseResultMap")
    List<Resource> getResourceList();


    @Select({
            "select r.* from sys_user_role ur, sys_role_resource rr, sys_resource r",
            "where ur.role_id = rr.role_id and rr.resource_id = r.id",
            "and r.status = 1",
            "and ur.user_id = #{userId,jdbcType=BIGINT}",
            "order by r.sort"
    })
    @ResultMap("BaseResultMap")
    List<Resource> getUserPermissions(Long userId);
}