package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.OrgMessage;

/**
 * OrgMessageService 接口
 * @author Mryang
 */
public interface OrgMessageService {
	/**
	 * 
	 * @param  OrgMessage 实体对象
	 * @return  带主键的OrgMessage
	 * @throws Exception 
	 */
	OrgMessage addOrgMessage(OrgMessage orgMessage) throws Exception;
	
	/**
	 * 
	 * @param OrgMessage 必须包含主键ID
	 * @return  OrgMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	OrgMessage getOrgMessageById(OrgMessage orgMessage) throws Exception;
	
	/**
	 * 
	 * @param OrgMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteOrgMessageById(OrgMessage orgMessage) throws Exception;
	
	/**
	 * @param OrgMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateOrgMessageById(OrgMessage orgMessage) throws Exception;
}
