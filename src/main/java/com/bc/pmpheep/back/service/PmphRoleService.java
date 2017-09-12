package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphRole;

/**
 * PmphRoleService接口
 * 
 * @author 曾庆峰
 *
 */
public interface PmphRoleService {
	/**
	 * 添加用户角色
	 * 
	 * @param pmphRole
	 *            添加的用户角色详细信息
	 * @return影响行数
	 */
	Integer addPmphRole(PmphRole pmphRole);

	/**
	 * 根据id删除
	 * @param ids 
	 * @return
	 */
	Integer deletePmphRoleById(String[] ids);

	Integer updatePmphRoleById(PmphRole pmphRole);

	PmphRole getPmphRoleByRoleName(String roleName);

}
