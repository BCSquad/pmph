package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphUserService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface PmphUserService {

    /**
     * 添加单个用户
     * 
     * @param user 要新增的用户
     * @return 被增加的用户
     */
    PmphUser add(PmphUser user) throws CheckedServiceException;

    /**
     * 批量添加用户角色关联表数据
     * 
     * @param user
     * @param rids
     */
    PmphUser add(PmphUser user, List<Long> rids) throws CheckedServiceException;

    /**
     * 根据 user_id 删除用户数据
     * 
     * @param id
     */
    Integer delete(Long id) throws CheckedServiceException;

    /**
     * 删除用户和用户绑定的角色信息
     * 
     * @param ids
     */
    Integer deleteUserAndRole(List<Long> ids) throws CheckedServiceException;

    /**
     * // TODO: 应该设置为一个事务 更新用户数据
     * 
     * 1、更新用户基本信息
     * 
     * 2、更新用户所属角色
     * 
     * （1）先删除所有的角色 （2）再添加绑定的角色
     * 
     * @param user
     * @param rids
     */
    PmphUser update(PmphUser user, List<Long> rids) throws CheckedServiceException;

    /**
     * 更新单个用户信息
     * 
     * @param user
     * @return
     */
    PmphUser update(PmphUser user) throws CheckedServiceException;

    /**
     * 根据主键 id 加载用户对象
     * 
     * @param id
     * @return
     */
    PmphUser get(Long id) throws CheckedServiceException;

    /**
     * 根据用户名加载用户对象（用于登录使用）
     * 
     * @param username
     * @return
     */
    PmphUser getByUsernameAndPassword(String username, String password)
    throws CheckedServiceException;

    /**
     * 根据用户名和真实姓名模糊查询社内用户
     * 
     * @param name 模糊查询参数
     * @return 经过分页的PmphUserManagerVO视图对象集合
     */
    Page<PmphUserManagerVO, String> getListByUsernameAndRealname(String name, int number, int size)
    throws CheckedServiceException;

    /**
     * 登录逻辑 1、先根据用户名查询用户对象 2、如果有用户对象，则继续匹配密码 如果没有用户对象，则抛出异常
     * 
     * @param username
     * @param password
     * @return
     */
    PmphUser login(String username, String password) throws CheckedServiceException;

    /**
     * 查询所有的用户对象列表
     * 
     * @return
     */
    List<PmphUser> getList() throws CheckedServiceException;

    /**
     * 根据角色 id 查询是这个角色的所有用户
     * 
     * @param id
     * @return
     */
    List<PmphUser> getListByRole(Long id) throws CheckedServiceException;

    /**
     * 查询指定用户 id 所拥有的权限
     * 
     * @param uid
     * @return
     */
    List<PmphPermission> getListAllResource(Long id) throws CheckedServiceException;

    /**
     * 查询指定用户所指定的角色字符串列表
     * 
     * @param uid
     * @return
     */
    List<String> getListRoleSnByUser(Long uid) throws CheckedServiceException;

    /**
     * 查询指定用户所绑定的角色列表
     * 
     * @param uid
     * @return
     */
    List<PmphRole> getListUserRole(Long uid) throws CheckedServiceException;

    /**
     * 
     * 
     * 功能描述：分页查询社内用户
     * 
     * @param page 传入的查询条件与分页条件
     * @return 返回已经分好页的结果集
     * @throws CheckedServiceException
     * 
     */
    Page<PmphUserManagerVO, PmphUserManagerVO> getListPmphUser(
    Page<PmphUserManagerVO, PmphUserManagerVO> page) throws CheckedServiceException;

    /**
     * 
     * 
     * 功能描述：后台社内用户管理页面修改作家用户
     * 
     * @param PmphUser 修改社内用户的属性
     * @return 是否成功
     * @throws CheckedServiceException
     * 
     */
    String updatePmphUserOfBack(PmphUserManagerVO pmphUserManagerVO) throws CheckedServiceException;
}
