package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphRolePermission;

/**
 * PmphRolePermissionService 接口
 * @author Mryang
 */
public interface  PmphRolePermissionService {

	/**
	 * 
	 * @param  PmphRolePermission 实体对象
	 * @return  带主键的PmphRolePermission
	 * @throws Exception 
	 */
	PmphGroup addPmphRolePermission (PmphRolePermission pmphRolePermission) throws Exception;
	
	/**
	 * 
	 * @param PmphRolePermission 必须包含主键ID
	 * @return  PmphRolePermission
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphRolePermission getPmphRolePermissionById(PmphRolePermission pmphRolePermission) throws Exception;
	
	/**
	 * 
	 * @param PmphRolePermission
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphRolePermissionById(PmphRolePermission pmphRolePermission) throws Exception;
	
	/**
	 * @param PmphRolePermission
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphRolePermissionById(PmphRolePermission pmphRolePermission) throws Exception;
}
