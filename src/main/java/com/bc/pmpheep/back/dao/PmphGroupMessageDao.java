package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphGroupMessage;

/**
 * PmphGroupMessage 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface  PmphGroupMessageDao {
	/**
	 * 
	 * @param  PmphGroupMessage 实体对象
	 * @return  带主键的 PmphGroupMessage
	 * @throws Exception 
	 */
	PmphGroupMember addPmphGroupMessage (PmphGroupMessage pmphGroupMessage) ;
	
	/**
	 * 
	 * @param PmphGroupMessage 必须包含主键ID
	 * @return  PmphGroupMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroupMessage getPmphGroupMessageById(PmphGroupMessage pmphGroupMessage) ;
	/**
	 * 
	 * @param PmphGroupMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupMessageById(PmphGroupMessage pmphGroupMessage) ;
	
	/**
	 * @param PmphGroupMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupMessageById(PmphGroupMessage pmphGroupMessage) ;
}
