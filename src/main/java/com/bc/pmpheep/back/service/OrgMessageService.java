package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.OrgMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgMessageService 接口
 * @author Mryang
 */
public interface OrgMessageService {
	/**
	 * 
	 * @param  orgMessage 实体对象
	 * @return  带主键的OrgMessage
	 * @throws CheckedServiceException
	 */
	OrgMessage addOrgMessage(OrgMessage orgMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  OrgMessage
	 * @throws CheckedServiceException
	 */
	OrgMessage getOrgMessageById(Long  id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgMessageById(Long  id) throws CheckedServiceException;
	
	/**
	 * @param orgMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrgMessage(OrgMessage orgMessage) throws CheckedServiceException;
}
