package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.OrgMessage;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface OrgMessageDao {
	/**
	 * 
	 * @param  orgMessage 实体对象
	 * @return  影响行数
	 */
	Integer addOrgMessage(OrgMessage orgMessage)  ;
	
	/**
	 * 
	 * @param id
	 * @return  OrgMessage
	  */
	OrgMessage getOrgMessageById(Long  id)  ;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	  */
	Integer deleteOrgMessageById(Long  id) ;
	
	/**
	 * @param orgMessage
	 * @return 影响行数
	  */
	Integer updateOrgMessage(OrgMessage orgMessage) ;
}
