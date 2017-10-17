package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupMessageService 接口
 * @author Mryang
 */
public interface PmphGroupMessageService {

	/**
	 * 
	 * @param  pmphGroupMessage 实体对象
	 * @return  带主键的 PmphGroupMessage
	 * @throws CheckedServiceException 
	 */
	PmphGroupMessage addPmphGroupMessage (PmphGroupMessage pmphGroupMessage) throws CheckedServiceException;
	
	/**
	 * 
	 * @param PmphGroupMessage 必须包含主键ID
	 * @return  PmphGroupMessage
	 * @throws CheckedServiceException
	 */
	PmphGroupMessage getPmphGroupMessageById(Long  id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id 
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphGroupMessageById(Long  id) throws CheckedServiceException;
	
	/**
	 * @param pmphGroupMessage 
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupMessage (PmphGroupMessage  pmphGroupMessage) throws CheckedServiceException;
	
	String addGroupMessage(String msgConrent, Long groupId, String sessionId) throws CheckedServiceException;
}
