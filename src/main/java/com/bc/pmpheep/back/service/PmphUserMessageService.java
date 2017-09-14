package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphUserMessage;

/**
 * PmphUserMessageService 接口
 * @author Mryang
 */
public interface   PmphUserMessageService {
	
	/**
	 * 
	 * @param  PmphUserMessage 实体对象
	 * @return  带主键的 PmphUserMessage
	 * @throws Exception 
	 */
	PmphUserMessage addPmphUserMessage (PmphUserMessage pmphUserMessage) throws Exception;
	
	/**
	 * 
	 * @param PmphUserMessage 必须包含主键ID
	 * @return  PmphUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphUserMessage getPmphUserMessageById(PmphUserMessage pmphUserMessage) throws Exception;
	
	/**
	 * 
	 * @param PmphUserMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphUserMessageById(PmphUserMessage pmphUserMessage) throws Exception;
	
	/**
	 * @param PmphUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphUserMessageById(PmphUserMessage pmphUserMessage) throws Exception;
}
