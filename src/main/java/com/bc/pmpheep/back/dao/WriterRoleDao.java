package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;

/**
 * WriterRole 实体类的数据访问层接口
 * 
 * @author 曾庆峰
 * 
 */
public interface WriterRoleDao {
    /**
     * 添加用户角色
     * 
     * @param pmphRole 添加的用户角色的详细信息
     * @return 影响的行数
     */

    Integer add(WriterRole role);

    Integer delete(Integer id);

    Integer batchDelete(@Param("ids") List<Integer> ids);

    WriterRole get(Integer id);

    Integer update(WriterRole role);

    List<WriterRole> getListRole();

    WriterUserRole getUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 为单个用户设置单个角色
     * 
     * @param userId
     * @param roleId
     * @return
     */
    Integer addUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 为单个用户设置多个角色
     * 
     * @param userId
     * @param roleIds
     * @return
     */
    Integer addUserRoles(@Param("userId") int userId, @Param("roleIds") List<Integer> roleIds);

    Integer deleteUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 删除某个用户的所有角色
     * 
     * @param uid
     */
    Integer deleteUserRoles(int uid);

    Integer batchDeleteRoleResource(@Param("roleIds") List<Integer> roleIds);

    /**
     * 根据角色id获取可以访问的所有资源
     * 
     * @param roleId
     * @return
     */
    List<WriterPermission> getListRoleResource(int roleId);

    Integer addRoleResource(@Param("roleId") int roleId, @Param("resourceId") int resourceId);

    Integer deleteRoleResource(@Param("roleId") int roleId, @Param("resourceId") int resorceId);

    WriterRolePermission getResourceRole(@Param("roleId") int roleId,
    @Param("resourceId") int resorceId);

    Integer deleteRoleAndUser(@Param("ids") List<Integer> ids);
}
