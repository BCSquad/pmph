package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphMessageService 接口
 * @author Mryang
 */
public interface  PmphMessageService {
	
	/**
	 * 
	 * @param  PmphMessage 实体对象
	 * @return  带主键的PmphMessage
	 * @throws CheckedServiceException
	 */
	PmphMessage addPmphMessage (PmphMessage pmphMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  PmphMessage
	 * @throws CheckedServiceException
	 */
	PmphMessage getPmphMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param PmphMessage
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * @param pmphMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphMessage(PmphMessage pmphMessage) throws CheckedServiceException;
}
