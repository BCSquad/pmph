package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	WriterUser add(WriterUser user) throws CheckedServiceException;

	/**
	 * 批量添加用户角色关联表数据
	 * 
	 * @param user
	 * @param rids
	 */
	WriterUser add(WriterUser user, List<Long> rids) throws CheckedServiceException;

	/**
	 * 根据 user_id 删除用户数据
	 * 
	 * @param id
	 */
	void delete(Long id) throws CheckedServiceException;

	/**
	 * 删除用户和用户绑定的角色信息
	 * 
	 * @param ids
	 */
	void deleteUserAndRole(List<Long> ids) throws CheckedServiceException;

	/**
	 * // TODO: 2016/9/18 应该设置为一个事务 更新用户数据 1、更新用户基本信息 2、更新用户所属角色 （1）先删除所有的角色
	 * （2）再添加绑定的角色
	 * 
	 * @param user
	 * @param rids
	 */
	WriterUser update(WriterUser user, List<Long> rids) throws CheckedServiceException;

	/**
	 * 更新单个用户信息
	 * 
	 * @param user
	 * @return
	 */
	WriterUser update(WriterUser user) throws CheckedServiceException;

	/**
	 * 根据主键 id 加载用户对象
	 * 
	 * @param id
	 * @return
	 */
	WriterUser get(Long id) throws CheckedServiceException;

	/**
	 * 根据用户名加载用户对象（用于登录使用）
	 * 
	 * @param username
	 * @return
	 */
	WriterUser getByUsername(String username) throws CheckedServiceException;

	/**
	 * 登录逻辑 1、先根据用户名查询用户对象 2、如果有用户对象，则继续匹配密码 如果没有用户对象，则抛出异常
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	WriterUser login(String username, String password) throws CheckedServiceException;

	/**
	 * 查询所有的用户对象列表
	 * 
	 * @return
	 */
	List<WriterUser> getList() throws CheckedServiceException;

	/**
	 * 根据角色 id 查询是这个角色的所有用户
	 * 
	 * @param id
	 * @return
	 */
	List<WriterUser> getListByRole(Long id) throws CheckedServiceException;

	/**
	 * 查询指定用户 id 所拥有的权限
	 * 
	 * @param uid
	 * @return
	 */
	List<WriterPermission> getListAllResource(Long uid) throws CheckedServiceException;

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
	List<WriterRole> getListUserRole(Long uid) throws CheckedServiceException;

	/**
	 * 
	 * <pre>
	 * 功能描述：分页查询作家用户
	 * 使用示范：
	 *
	 * @param page 传入的查询条件
	 * @return
	 * </pre>
	 */
	Page<WriterUserManagerVO,Map<String, String>> getListWriterUser(Page<WriterUserManagerVO,Map<String, String>> page) throws CheckedServiceException;
	/**
	 * 
	 * <pre>
	 * 功能描述：分页查询作家用户
	 * 使用示范：
	 *
	 * @param page 传入的查询条件
	 * @return
	 * @throws CheckedServiceException
	 * </pre>
	 */
	Page<WriterUserManagerVO, Map<String, String>> getListWriter(Page<WriterUser, Map<String,String>> page) throws CheckedServiceException, ReflectiveOperationException;
}
