package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphRoleMaterial;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * 
 * 功能描述：角色-教材权限关联表 方法
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月23日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public interface PmphRoleMaterialService {
	/**
	 * 
	 * 
	 * 功能描述：添加一个角色的教材权限
	 *
	 * @param pmphRoleMaterial
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Integer addPmphRoleMaterial(PmphRoleMaterial pmphRoleMaterial) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据角色id修改角色教材权限
	 *
	 * @param pmphRoleMaterial
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Integer updatePmphRoleMaterialByRoleId(PmphRoleMaterial pmphRoleMaterial) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：根据角色查询教材权限
	 *
	 * @param roleId
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	PmphRoleMaterial getPmphRoleMaterialByRoleId(Long roleId) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：删除角色时根据角色id 删除教材权限
	 *
	 * @param roleId
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	Integer deletePmphRoleMaterialByRoleId(Long roleId) throws CheckedServiceException;
}
