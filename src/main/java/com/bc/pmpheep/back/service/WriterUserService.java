package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;

/**
 * WriterUserService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface WriterUserService {

    /**
     * 添加单个用户
     * 
     * @param user
     */
    WriterUser add(WriterUser user) throws Exception;

    /**
     * 批量添加用户角色关联表数据
     * 
     * @param user
     * @param rids
     */
    WriterUser add(WriterUser user, List<Integer> rids) throws Exception;

    /**
     * 根据 user_id 删除用户数据
     * 
     * @param id
     */
    void delete(int id) throws Exception;

    /**
     * 删除用户和用户绑定的角色信息
     * 
     * @param ids
     */
    void deleteUserAndRole(List<Integer> ids) throws Exception;

    /**
     * // TODO: 2016/9/18 应该设置为一个事务 更新用户数据 1、更新用户基本信息 2、更新用户所属角色 （1）先删除所有的角色 （2）再添加绑定的角色
     * 
     * @param user
     * @param rids
     */
    WriterUser update(WriterUser user, List<Integer> rids) throws Exception;

    /**
     * 更新单个用户信息
     * 
     * @param user
     * @return
     */
    WriterUser update(WriterUser user) throws Exception;

    /**
     * 根据主键 id 加载用户对象
     * 
     * @param id
     * @return
     */
    WriterUser get(int id) throws Exception;

    /**
     * 根据用户名加载用户对象（用于登录使用）
     * 
     * @param username
     * @return
     */
    WriterUser getByUsername(String username) throws Exception;

    /**
     * 登录逻辑 1、先根据用户名查询用户对象 2、如果有用户对象，则继续匹配密码 如果没有用户对象，则抛出异常
     * 
     * @param username
     * @param password
     * @return
     */
    WriterUser login(String username, String password) throws Exception;

    /**
     * 查询所有的用户对象列表
     * 
     * @return
     */
    List<WriterUser> getList() throws Exception;

    /**
     * 根据角色 id 查询是这个角色的所有用户
     * 
     * @param id
     * @return
     */
    List<WriterUser> getListByRole(int id) throws Exception;

    /**
     * 查询指定用户 id 所拥有的权限
     * 
     * @param uid
     * @return
     */
    List<WriterPermission> getListAllResource(int uid) throws Exception;

    /**
     * 查询指定用户所指定的角色字符串列表
     * 
     * @param uid
     * @return
     */
    List<String> getListRoleSnByUser(int uid) throws Exception;

    /**
     * 查询指定用户所绑定的角色列表
     * 
     * @param uid
     * @return
     */
    List<WriterRole> getListUserRole(int uid) throws Exception;

}
