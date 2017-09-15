package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * OrgDao实体类数据访问层接口
 * @author mryang
 */
public interface OrgDao {
	
	/**
	 * 
	 * @param org 实体对象
	 * @return  影像行数
	 * @throws CheckedServiceException 
	 */
	Integer addOrg(Org org) ;
	
	/**
	 * 
	 * @param id
	 * @return  Org
	 * @throws CheckedServiceException
	 */
	Org getOrgById(Long  id) ;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgById(Long  id) ;
	
	/**
	 * @param org
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrg(Org org) ;
	
	
}
