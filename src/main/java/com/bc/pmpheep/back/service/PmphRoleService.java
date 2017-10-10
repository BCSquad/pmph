package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphRoleService接口
 * 
 * @author 曾庆峰
 * 
 */
public interface PmphRoleService {
    /**
     * 添加单个角色对象
     * 
     * @param role
     */
    Integer add(PmphRole role) throws CheckedServiceException;

    /**
     * 根据角色 id 删除单个角色对象
     * 
     * @param id
     */
    Integer delete(Long id) throws CheckedServiceException;

    /**
     * 根据角色 id 删除对应资源
     * 
     * @param ids
     * @return
     */
    Integer deleteRoleAndResource(List<Long> ids) throws CheckedServiceException;

    /**
     * 根据 id 加载角色对象
     * 
     * @param id
     * @return
     */
    PmphRole get(Long id) throws CheckedServiceException;

    /**
     * 查询所有角色对象的列表
     * 
     * @return
     */
    List<PmphRole> getList() throws CheckedServiceException;

    /**
     * 更新单个角色对象
     * 
     * @param role
     */
    Integer update(PmphRole role) throws CheckedServiceException;

    /**
     * 查询所有角色列表
     * 
     * @return
     */
    List<PmphRole> listRole() throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 加载一条用户角色关系数据
     * 
     * @param uid
     * @param roleId
     * @return
     */
    PmphUserRole getUserRole(Long uid, Long roleId) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 插入一条用户角色关系数据
     * 
     * @param uid
     * @param roleId
     */
    Integer addUserRole(Long uid, Long roleId) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 删除一条用户角色关系数据
     * 
     * @param uid
     * @param roleId
     */
    Integer deleteUserRole(Long uid, Long roleId) throws CheckedServiceException;

    /**
     * 删除某个用户的所有角色
     * 
     * @param uid
     */
    Integer deleteUserRoles(Long uid) throws CheckedServiceException;

    /**
     * 根据角色id获取可以访问的所有资源
     * 
     * @param roleId
     * @return
     */
    List<PmphPermission> getListRoleResource(Long roleId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据角色ID查询对应权限
     * 使用示范：
     *
     * @param roleId
     * @return
     * </pre>
     */
    List<PmphRolePermission> getListPmphRolePermission(Long roleId);

    /**
     * 根据角色 id 和权限 id 增加一条用户权限关联数据
     * 
     * @param roleId
     * @param permissionIds
     */
    Integer addRoleResource(Long roleId, List<Long> permissionIds) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 删除一条用户权限关联数据
     * 
     * @param roleId
     * @param resId
     */
    Integer deleteRoleResource(Long roleId, Long resId) throws CheckedServiceException;

    /**
     * 根据角色 id 删除用户权限关联数据
     * 
     * @param roleId
     * @param resId
     */
    Integer deleteRoleResourceByRoleId(Long roleId) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 查询一条用户权限关联数据
     * 
     * @param roleId
     * @param resId
     * @return
     */
    PmphRolePermission getResourceRole(Long roleId, Long resId) throws CheckedServiceException;

    Integer deleteRoleAndUser(List<Long> ids) throws CheckedServiceException;
}
