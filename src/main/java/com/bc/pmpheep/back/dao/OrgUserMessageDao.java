package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.OrgUserMessage;

/**
 * OrgUserMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface OrgUserMessageDao {
	/**
	 * 
	 * @param  OrgUserMessage 实体对象
	 * @return 影响行数
	 * @throws Exception 
	 */
	Integer addOrgUserMessage (OrgUserMessage orgUserMessage);
	
	/**
	 * 
	 * @param OrgUserMessage 必须包含主键ID
	 * @return  OrgUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	OrgUserMessage getOrgUserMessageById(OrgUserMessage orgUserMessage);
	
	/**
	 * 
	 * @param OrgUserMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteOrgUserMessageById(OrgUserMessage orgUserMessage) ;
	
	/**
	 * @param OrgUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateOrgUserMessageById(OrgUserMessage orgUserMessage) ;
}
