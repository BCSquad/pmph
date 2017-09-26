package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.OrgUserMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgUserMessageService  接口
 * @author Mryang
 */
public interface   OrgUserMessageService {

	/**
	 * 
	 * @param  orgUserMessage 实体对象
	 * @return  带主键的 OrgUserMessage
	 * @throws CheckedServiceException 
	 */
	OrgUserMessage addOrgUserMessage (OrgUserMessage orgUserMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  OrgUserMessage
	 * @throws CheckedServiceException
	 */
	OrgUserMessage getOrgUserMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param OrgUserMessage
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgUserMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * @param OrgUserMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrgUserMessage(OrgUserMessage orgUserMessage) throws CheckedServiceException;
}
