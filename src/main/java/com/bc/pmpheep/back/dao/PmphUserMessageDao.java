package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphUserMessage;

/**
 * PmphUserMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphUserMessageDao {
	/**
	 * 
	 * @param  PmphUserMessage 实体对象
	 * @return  带主键的 PmphUserMessage
	 * @throws Exception 
	 */
	PmphUserMessage addPmphUserMessage (PmphUserMessage pmphUserMessage) ;
	
	/**
	 * 
	 * @param PmphUserMessage 必须包含主键ID
	 * @return  PmphUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphUserMessage getPmphUserMessageById(PmphUserMessage pmphUserMessage) ;
	
	/**
	 * 
	 * @param PmphUserMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphUserMessageById(PmphUserMessage pmphUserMessage) ;
	
	/**
	 * @param PmphUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphUserMessageById(PmphUserMessage pmphUserMessage) ;
}
