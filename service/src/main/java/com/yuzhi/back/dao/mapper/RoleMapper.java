package com.yuzhi.back.dao.mapper;

import com.yuzhi.back.dao.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {
    @Delete({
        "delete from sys_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_role (id, name, ",
        "status, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Role record);

    int insertSelective(Role record);

    @Select({
        "select",
        "id, name, status, create_time, update_time",
        "from sys_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    @Update({
        "update sys_role",
        "set name = #{name,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Role record);

    //------------------------------------

    @Select({
            "select",
            "id, name, status, create_time, update_time",
            "from sys_role"
    })
    @ResultMap("BaseResultMap")
    List<Role> getRoleList();

    @Select({
            "select sys_role.* from sys_role, sys_user_role",
            "where sys_role.id = sys_user_role.role_id",
            "and user_id = #{userId,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    List<Role> getUserRoles(Long userId);

    @Update({
            "update sys_role",
            "set status = #{status,jdbcType=TINYINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateRoleStatus(@Param("id") Long id, @Param("status") Integer status);


    int batchDelete(List<Long> ids);
}