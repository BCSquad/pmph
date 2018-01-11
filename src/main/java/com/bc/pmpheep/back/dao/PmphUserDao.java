/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.vo.PmphEditorVO;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;

/**
 * PmphUser实体类数据访问层接口
 * 
 * @author L.X <gugia@qq.com>
 */
public interface PmphUserDao {

	/**
	 * 添加一个用户
	 * 
	 * @param pmphUser
	 *            添加用户的详细信息
	 * @return 影响的行数
	 */
	Integer add(PmphUser user);

	Integer update(PmphUser user);

	Integer delete(Long id);

	Integer batchDelete(@Param("ids") List<Long> ids);

	PmphUser get(Long id);
	
	/**
	 * 根据主键 获取用户要更新的信息 
	 * @author Mryang
	 * @createDate 2017年12月11日 下午5:27:20
	 * @param id
	 * @return
	 */
	PmphUser getInfo(Long id);
	
	/**
	 * 根据id和原密码修改密码
	 * @author Mryang
	 * @createDate 2017年12月12日 上午8:46:44
	 * @param map
	 * @return
	 */
	Integer updatePassword (Map<String,Object> map);
	

	List<PmphUser> getListUser();

	List<PmphUser> getListByUsernameAndRealname(@Param("name") String name, @Param("start") int start,
			@Param("size") int size);

	PmphUser getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	/**
	 * 根据角色 id 查询所有是该角色的用户列表
	 * 
	 * @param rid
	 * @return
	 */
	List<PmphUser> getListByRole(Long rid);

	List<PmphPermission> getListAllResources(Long uid);

	List<String> getListRoleSnByUser(Long uid);

	List<PmphRole> getListUserRole(Long uid);

	/**
	 * 
	 * <pre>
	 * 功能描述：根据用户Id查询对应权限
	 * 使用示范：
	 *
	 * @param userId
	 * @return
	 * </pre>
	 */
	List<Long> getPmphUserPermissionByUserId(Long userId);

	/**
	 * 
	 * <pre>
	 * 功能描述：根据用户Id查询对应教材权限
	 * 使用示范：
	 *
	 * @param userId
	 * @return
	 * </pre>
	 */
	List<Integer> getMaterialPermissionByUserId(Long userId);

	/**
	 * 
	 * 功能描述：查询表单的数据总条数
	 * 
	 * @return 表单的总条数
	 */
	Long getPmphUserCount();

	/**
	 * 
	 * 
	 * 功能描述： 获取社内用户的
	 * 
	 * @param page
	 * @return
	 * 
	 */
	Integer getListPmphUserTotal(PageParameter<PmphUserManagerVO> pageParameter);

	/**
	 * 
	 * 
	 * 功能描述： 获取社内用户的
	 * 
	 * @param page
	 * @return
	 * 
	 */
	List<PmphUserManagerVO> getListPmphUser(PageParameter<PmphUserManagerVO> pageParameter);

	/**
	 * 
	 * 
	 * 功能描述：
	 * 
	 * @param user
	 * @return
	 * 
	 */
	Integer updatePmphUserOfBack(PmphUserManagerVO pmphUserManagerVO);

	/**
	 * 
	 * 
	 * 功能描述：根据部门id判断部门中是否还有用户
	 *
	 * @param departmentId
	 *            部门id
	 * 
	 * @return
	 *
	 */
	List<PmphUser> getPmphUserByDepartmentId(Long departmentId);
	
	/**
	 * 
	 * Description:统计部门下的编辑数量
	 * @author:lyc
	 * @date:2017年12月27日下午4:14:53
	 * @param 
	 * @return Integer
	 */
	Integer totalEditors(@Param("departmentId") Long departmentId, @Param("realName") String realName);

	/**
	 * 
	 * Description:选题申报部门主任获取部门编辑列表
	 * @author:lyc
	 * @date:2017年12月27日下午4:03:55
	 * @param 
	 * @return List<PmphEditorVO>
	 */
	List<PmphEditorVO> listEditors(@Param("departmentId") Long departmentId, @Param("realName") String realName,
			@Param("pageSize") Integer pageSize, @Param("start") Integer start);
	
	/**
	 * 根据用户名获取用户
	 * tyc
	 * @param username
	 * @return
	 */
	PmphUser getPmphUser(String username);
}
