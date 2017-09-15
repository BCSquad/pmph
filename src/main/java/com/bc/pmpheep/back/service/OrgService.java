package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * OrgService 接口
 * @author Mryang
 *
 */
public interface OrgService {
	
	/**
	 * 
	 * @param org 实体对象
	 * @return  Org 带主键
	 * @throws CheckedServiceException 
	 */
	Org addOrg(Org org) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  Org
	 * @throws CheckedServiceException
	 */
	Org getOrgById(Long  id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgById(Long  id) throws CheckedServiceException;
	
	/**
	 * @param org
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrg(Org org) throws CheckedServiceException;
	
	
}
