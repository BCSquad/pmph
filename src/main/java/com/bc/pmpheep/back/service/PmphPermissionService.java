package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphPermissionService 接口
 * @author Mryang
 */
public interface PmphPermissionService {
	/**
	 * 
	 * @param  PmphPermission 实体对象
	 * @return  带主键的PmphPermission
	 * @throws Exception 
	 */
	PmphPermission addPmphPermission(PmphPermission pmphPermission) throws Exception;
	
	/**
	 * 
	 * @param PmphPermission 必须包含主键ID
	 * @return  PmphPermission
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphPermission getPmphPermissionById(PmphPermission pmphPermission) throws Exception;
	
	/**
	 * 
	 * @param PmphPermission
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphPermissionById(PmphPermission pmphPermission) throws Exception;
	
	/**
	 * @param PmphPermission
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphPermissionById(PmphPermission pmphPermission) throws Exception;
}
