package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphUserMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphUserMessageService 接口
 * @author Mryang
 */
public interface   PmphUserMessageService {
	
	/**
	 * 新增 一个         pmphUserMessage 
	 * @param  pmphUserMessage 
	 * @return  带主键的 PmphUserMessage
	 * @throws CheckedServiceException 
	 */
	PmphUserMessage addPmphUserMessage (PmphUserMessage pmphUserMessage)  throws CheckedServiceException;
	
	/**
	 * 根据id查询 pmphUserMessage
	 * @param id
	 * @return  PmphUserMessage
	 * @throws CheckedServiceException
	 */
	PmphUserMessage getPmphUserMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * 根据id删除PmphUserMessage
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphUserMessageById(Long id) throws CheckedServiceException;
	
	/**
	 * 根据带主键修改 pmphUserMessage（必须带主键）
	 * @param pmphUserMessage 
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphUserMessage(PmphUserMessage pmphUserMessage) throws CheckedServiceException;
}
