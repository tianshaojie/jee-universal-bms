package com.xuanniu.bms.dao.mapper;

import com.xuanniu.bms.dao.entity.RoleResource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RoleResourceMapper {
    @Delete({
        "delete from sys_role_resource",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_role_resource (id, role_id, ",
        "resource_id)",
        "values (#{id,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{resourceId,jdbcType=BIGINT})"
    })
    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    @Select({
        "select",
        "id, role_id, resource_id",
        "from sys_role_resource",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    RoleResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleResource record);

    @Update({
        "update sys_role_resource",
        "set role_id = #{roleId,jdbcType=BIGINT},",
          "resource_id = #{resourceId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RoleResource record);
}