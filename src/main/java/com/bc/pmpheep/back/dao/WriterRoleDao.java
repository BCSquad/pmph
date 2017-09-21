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

    Integer add(WriterRole writerRole);

    Integer delete(Long id);

    Integer batchDelete(@Param("ids") List<Long> ids);

    WriterRole get(Long id);

    Integer update(WriterRole role);

    List<WriterRole> getListRole();

    WriterUserRole getUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 为单个用户设置单个角色
     * 
     * @param userId
     * @param roleId
     * @return
     */
    Integer addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 为单个用户设置多个角色
     * 
     * @param userId
     * @param roleIds
     * @return
     */
    Integer addUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    Integer deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除某个用户的所有角色
     * 
     * @param uid
     */
    Integer deleteUserRoles(Long uid);

    Integer batchDeleteRoleResource(@Param("roleIds") List<Long> roleIds);

    /**
     * 根据角色id获取可以访问的所有资源
     * 
     * @param roleId
     * @return
     */
    List<WriterPermission> getListRoleResource(Long roleId);

    Integer addRoleResource(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    Integer deleteRoleResource(@Param("roleId") Long roleId,
    @Param("permissionId") Long permissionId);

    WriterRolePermission getResourceRole(@Param("roleId") Long roleId,
    @Param("permissionId") Long permissionId);

    Integer deleteRoleAndUser(@Param("ids") List<Long> ids);

    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getWriterRoleCount();
}
