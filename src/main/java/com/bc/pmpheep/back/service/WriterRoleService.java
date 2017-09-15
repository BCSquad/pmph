package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterRoleService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface WriterRoleService {
    /**
     * 添加单个角色对象
     * 
     * @param role
     */
    WriterRole add(WriterRole writerRole) throws CheckedServiceException;

    /**
     * 根据角色 id 删除单个角色对象
     * 
     * @param id
     */
    Integer delete(int id) throws CheckedServiceException;

    /**
     * 
     * @param ids
     * @return
     */
    void deleteRoleAndResource(List<Integer> ids) throws CheckedServiceException;

    /**
     * 根据 id 加载角色对象
     * 
     * @param id
     * @return
     */
    WriterRole get(int id) throws CheckedServiceException;

    /**
     * 查询所有角色对象的列表
     * 
     * @return
     */
    List<WriterRole> getList() throws CheckedServiceException;

    /**
     * 更新单个角色对象
     * 
     * @param role
     */
    Integer update(WriterRole role) throws CheckedServiceException;

    /**
     * 查询所有角色列表
     * 
     * @return
     */
    List<WriterRole> getListRole() throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 加载一条用户角色关系数据
     * 
     * @param uid
     * @param roleId
     * @return
     */
    WriterUserRole getUserRole(int uid, int roleId) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 插入一条用户角色关系数据
     * 
     * @param uid
     * @param roleId
     */
    void addUserRole(int uid, int roleId) throws CheckedServiceException;

    /**
     * 根据用户 id 和角色 id 删除一条用户角色关系数据
     * 
     * @param uid
     * @param roleId
     */
    void deleteUserRole(int uid, int roleId) throws CheckedServiceException;

    /**
     * 删除某个用户的所有角色
     * 
     * @param uid
     */
    void deleteUserRoles(int uid) throws CheckedServiceException;

    /**
     * 根据角色id获取可以访问的所有资源
     * 
     * @param roleId
     * @return
     */
    List<WriterPermission> getListRoleResource(int roleId) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 增加一条用户权限关联数据
     * 
     * @param roleId
     * @param resId
     */
    void addRoleResource(int roleId, int resId) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 删除一条用户权限关联数据
     * 
     * @param roleId
     * @param resId
     */
    void deleteRoleResource(int roleId, int resId) throws CheckedServiceException;

    /**
     * 根据角色 id 和权限 id 查询一条用户权限关联数据
     * 
     * @param roleId
     * @param resId
     * @return
     */
    WriterRolePermission getResourceRole(int roleId, int resId) throws CheckedServiceException;

    Integer deleteRoleAndUser(List<Integer> ids) throws CheckedServiceException;
}
