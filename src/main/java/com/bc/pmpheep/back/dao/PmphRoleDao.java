package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.vo.PmphRoleVO;

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

    Integer delete(Long id);

    Integer batchDelete(@Param("ids") List<Long> ids);

    PmphRole get(Long id);

    Integer update(PmphRole role);

    List<PmphRole> getListRole(@Param("roleName") String roleName);

    List<PmphRoleVO> listRole();

    List<PmphRolePermission> getPmphRolePermissionByRoleId(Long roleId);

    List<Long> getPmphRolePermissionIdByRoleId(Long roleId);

    /**
     * 
     * <pre>
     * 功能描述：根据用户Id查询角色信息
     * 使用示范：
     *
     * @param userId 用户ID
     * @return PmphRole 对象
     * </pre>
     */
    PmphRole getPmphRoleByUserId(Long userId);

    /**
     * 
     * 
     * 功能描述：根据用户id获取该用户所属角色
     * 
     * @param userId 用户id
     * @return 所属角色对象
     * 
     */
    List<PmphRoleVO> listPmphUserRoleByUserId(Long userId);

    PmphUserRole getUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

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
    List<PmphPermission> getListRoleResource(Long roleId);

    Integer addRoleResource(List<PmphRolePermission> pmphRolePermission);

    Integer deleteRoleResource(@Param("roleId") Long roleId,
    @Param("permissionId") Long permissionId);

    Integer deleteRoleResourceByRoleId(@Param("roleId") Long roleId);

    PmphRolePermission getResourceRole(@Param("roleId") Long roleId,
    @Param("permissionId") Long permissionId);

    Integer deleteRoleAndUser(@Param("ids") List<Long> ids);

    /**
     * 
     * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * &#64;return 表单的总条数
	 * </pre>
     */
    Long getPmphRoleCount();

    /**
     * 
     * 
     * 功能描述：根据角色名称获取角色id
     * 
     * @param roleName 角色名称
     * @return 角色id
     * 
     */
    Long getPmphRoleId(String roleName);
}
