package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.OrgMessage;

/**
 * OrgMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface OrgMessageDao {
	/**
	 * 
	 * @param  OrgMessage 实体对象
	 * @return  带主键的OrgMessage
	 * @throws Exception 
	 */
	OrgMessage addOrgMessage(OrgMessage orgMessage);
	
	/**
	 * 
	 * @param OrgMessage 必须包含主键ID
	 * @return  OrgMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	OrgMessage getOrgMessageById(OrgMessage orgMessage) ;
	
	/**
	 * 
	 * @param OrgMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteOrgMessageById(OrgMessage orgMessage) ;
	
	/**
	 * @param OrgMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateOrgMessageById(OrgMessage orgMessage) ;
}
