package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgUserService 接口
 * @author Mryang
 */
public interface  OrgUserService {
	/**
	 * 
	 * @param orgUser 实体对象
	 * @return   带主键的 OrgUser
	 * @throws CheckedServiceException 
	 */
	OrgUser addOrgUser(OrgUser orgUser) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  OrgUser
	 * @throws CheckedServiceException
	 */
	OrgUser getOrgUserById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgUserById(Long id) throws CheckedServiceException;
	
	/**
	 * @param orgUser
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrgUser(OrgUser orgUser) throws CheckedServiceException;
}
