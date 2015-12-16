package com.yuzhi.back.dao.mapper;

import com.yuzhi.back.dao.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserRoleMapper {
    @Delete({
        "delete from sys_user_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_user_role (id, user_id, ",
        "role_id)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{roleId,jdbcType=BIGINT})"
    })
    int insert(UserRole record);

    int insertSelective(UserRole record);

    @Select({
        "select",
        "id, user_id, role_id",
        "from sys_user_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    @Update({
        "update sys_user_role",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "role_id = #{roleId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRole record);

    //---------------------------

    @Select({
         "select count(1) from sys_user_role",
         "where role_id = #{roleId,jdbcType=BIGINT}"
    })
    int getRoleUserCount(Long roleId);

    @Delete({
            "delete from sys_user_role",
            "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int deleteByUserId(Long userId);

    int batchInsert(List<UserRole> list);
}