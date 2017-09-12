package com.bc.pmpheep.back.dao.pmphrole;

import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.PmphRole;

/**
 * PmphRole实体类的数据实现层接口
 * 
 * @author 曾庆峰
 *
 */
@Component
public interface PmphRoleDao {
	/**
	 * 添加用户角色
	 * 
	 * @param pmphRole
	 *            添加的用户角色的详细信息
	 * @return 影响的行数
	 */
	Integer addPmphRole(PmphRole pmphRole);

	/**
	 * 根据角色Id删除角色
	 * 
	 * @param ids
	 *            需要删除的角色的id数组
	 * @return 影响的行数
	 */
	Integer deletePmphRoleById(String[] ids);

	/**
	 * 根据角色的Id修改角色详细信息
	 * 
	 * @param PmphRole
	 *            新的角色详细信息
	 * @return 影响的行数
	 */
	Integer updatePmphRoleById(PmphRole PmphRole);

	/**
	 * 根据角色名称精确的查询角色详细信息
	 * 
	 * @param roleName
	 *            角色名称
	 * @return 需要的角色详细信息
	 */
	PmphRole getPmphRoleByRoleName(String roleName);
}
