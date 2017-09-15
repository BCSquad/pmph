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
	 * @param  orgUserMessage 实体对象
	 * @return   影响行数
	*/
	Integer addOrgUserMessage (OrgUserMessage orgUserMessage) ;
	
	/**
	 * 
	 * @param id
	 * @return  OrgUserMessage
	 */
	OrgUserMessage getOrgUserMessageById(Long id);
	
	/**
	 * 
	 * @param OrgUserMessage
	 * @return  影响行数
	 */
	Integer deleteOrgUserMessageById(Long id) ;
	
	/**
	 * @param OrgUserMessage
	 * @return 影响行数
	 */
	Integer updateOrgUserMessage(OrgUserMessage orgUserMessage) ;
}
