package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;

/**
 * PmphRole实体类的数据实现层接口
 * 
 * @author 曾庆峰
 * 
 */
public interface PmphRoleDao {
    /**
     * 添加用户角色
     * 
     * @param pmphRole 添加的用户角色的详细信息
     * @return 影响的行数
     */

    Integer add(PmphRole role);

    Integer delete(Integer id);

    Integer batchDelete(@Param("ids") List<Integer> ids);

    PmphRole get(Integer id);

    Integer update(PmphRole role);

    List<PmphRole> getListRole();

    PmphUserRole getUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

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
    List<PmphPermission> getListRoleResource(int roleId);

    Integer addRoleResource(@Param("roleId") int roleId, @Param("resourceId") int resourceId);

    Integer deleteRoleResource(@Param("roleId") int roleId, @Param("resourceId") int resorceId);

    PmphRolePermission getResourceRole(@Param("roleId") int roleId,
    @Param("resourceId") int resorceId);

    Integer deleteRoleAndUser(@Param("ids") List<Integer> ids);
}
