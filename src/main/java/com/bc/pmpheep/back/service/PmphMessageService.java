package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphMessage;

/**
 * PmphMessageService 接口
 * @author Mryang
 */
public interface  PmphMessageService {
	
	/**
	 * 
	 * @param  PmphMessage 实体对象
	 * @return  带主键的PmphMessage
	 * @throws Exception 
	 */
	PmphMessage addPmphMessage (PmphMessage pmphMessage) throws Exception;
	
	/**
	 * 
	 * @param PmphMessage 必须包含主键ID
	 * @return  PmphMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphMessage getPmphMessageById(PmphMessage pmphMessage) throws Exception;
	
	/**
	 * 
	 * @param PmphMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphMessageById(PmphMessage pmphMessage) throws Exception;
	
	/**
	 * @param PmphMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphMessageById(PmphMessage pmphMessage) throws Exception;
}
