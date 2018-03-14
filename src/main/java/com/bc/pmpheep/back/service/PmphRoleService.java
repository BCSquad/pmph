package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.vo.PmphRoleVO;
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
     * @param role 角色对象
     */
    PmphRole addPmphRole(PmphRole role) throws CheckedServiceException;

    /**
     * 添加单个角色对象
     * 
     * @param role 角色对象
     */
    PmphRole add(PmphRole role, Long[] ids) throws CheckedServiceException;

    /**
     * 根据角色 id 删除单个角色对象
     * 
     * @param id 角色ID
     */
    Integer delete(Long id) throws CheckedServiceException;

    /**
     * 根据角色 id 删除对应资源
     * 
     * @param ids 角色ID集合
     * @return
     */
    Integer deleteRoleAndResource(List<Long> ids) throws CheckedServiceException;

    /**
     * 根据 id 加载角色对象
     * 
     * @param id 角色ID
     * @return
     */
    PmphRole get(Long id) throws CheckedServiceException;
    
    /**
     * 根据 roleName加载角色对象
     * @return
     */
    PmphRole getByName(String roleName) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据用户id获取角色对象
     * 使用示范：
     *
     * @param userId  用户id
     * @return PmphRole 对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<PmphRole> getPmphRoleByUserId(Long userId) throws CheckedServiceException;

    /**
     * 查询所有角色对象的列表
     * 
     * @return
     */
    List<PmphRole> getList(String roleName) throws CheckedServiceException;

    /**
     * 更新单个角色对象
     * 
     * @param role 角色对象
     */
    Integer update(PmphRole role) throws CheckedServiceException;

    /**
     * 查询所有角色列表
     * 
     * @return
     */
    List<PmphRoleVO> getListRole() throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 加载一条用户角色关系数据
     * 
     * @param uid 用户ID
     * @param roleId 角色ID
     * @return
     */
    PmphUserRole getUserRole(Long uid, Long roleId) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 插入一条用户角色关系数据
     * 
     * @param uid 用户ID
     * @param roleId 角色ID
     */
    Integer addUserRole(Long uid, Long roleId) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 删除一条用户角色关系数据
     * 
     * @param uid
     * @param roleId 角色ID
     */
    Integer deleteUserRole(Long uid, Long roleId) throws CheckedServiceException;

    /**
     * 删除某个用户的所有角色
     * 
     * @param uid 用户ID
     */
    Integer deleteUserRoles(Long uid) throws CheckedServiceException;

    /**
     * 根据角色id获取可以访问的所有资源
     * 
     * @param roleId 角色ID
     * @return
     */
    List<PmphPermission> getListRoleResource(Long roleId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：根据角色ID查询对应权限对象集合
     * 使用示范：
     *
     * @param roleId 角色ID
     * @return
     * </pre>
     */
    List<PmphRolePermission> getListPmphRolePermission(Long roleId);

    /**
     * 
     * <pre>
     * 功能描述：根据角色ID查询对应权限Id集合
     * 使用示范：
     *
     * @param roleId 角色ID
     * @return
     * </pre>
     */
    List<Long> getListPmphRolePermissionIdByRoleId(Long roleId);

    /**
     * 根据角色 id 和权限 id 增加一条用户权限关联数据
     * 
     * @param roleId 角色ID
     * @param permissionIds 权限ID集合
     */
    Integer addRoleResource(Long roleId, List<Long> permissionIds) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 删除一条用户权限关联数据
     * 
     * @param roleId 角色ID
     * @param resId 权限ID
     */
    Integer deleteRoleResource(Long roleId, Long resId) throws CheckedServiceException;

    /**
     * 根据角色 id 删除用户权限关联数据
     * 
     * @param roleId 角色ID
     * @param resId 权限ID
     */
    Integer deleteRoleResourceByRoleId(Long roleId) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 查询一条用户权限关联数据
     * 
     * @param roleId 角色ID
     * @param resId 权限ID
     * @return
     */
    PmphRolePermission getResourceRole(Long roleId, Long resId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @param ids 角色ID集合
     * @return
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteRoleAndUser(List<Long> ids) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 加载一条用户角色关系数据集合
     * 
     * @param userid
     * @param roleId
     * @return
     */
    List<PmphUserRole> getUserRoleList(Long userId, Long roleId) throws CheckedServiceException;
}
