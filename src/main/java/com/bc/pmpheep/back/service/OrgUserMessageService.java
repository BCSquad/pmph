package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.OrgUserMessage;

/**
 * OrgUserMessageService  接口
 * @author Mryang
 */
public interface   OrgUserMessageService {
	/**
	 * 
	 * @param  OrgUserMessage 实体对象
	 * @return  带主键的 OrgUserMessage
	 * @throws Exception 
	 */
	OrgUserMessage addOrgUserMessage (OrgUserMessage orgUserMessage) throws Exception;
	
	/**
	 * 
	 * @param OrgUserMessage 必须包含主键ID
	 * @return  OrgUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	OrgUserMessage getOrgUserMessageById(OrgUserMessage orgUserMessage) throws Exception;
	
	/**
	 * 
	 * @param OrgUserMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteOrgUserMessageById(OrgUserMessage orgUserMessage) throws Exception;
	
	/**
	 * @param OrgUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateOrgUserMessageById(OrgUserMessage orgUserMessage) throws Exception;
}
