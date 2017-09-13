package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphGroupMessage;

/**
 * PmphGroupMessageService 接口
 * @author Mryang
 */
public interface PmphGroupMessageService {

	/**
	 * 
	 * @param  PmphGroupMessage 实体对象
	 * @return  带主键的 PmphGroupMessage
	 * @throws Exception 
	 */
	PmphGroupMember addPmphGroupMessage (PmphGroupMessage pmphGroupMessage) throws Exception;
	
	/**
	 * 
	 * @param PmphGroupMessage 必须包含主键ID
	 * @return  PmphGroupMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroupMessage getPmphGroupMessageById(PmphGroupMessage pmphGroupMessage) throws Exception;
	/**
	 * 
	 * @param PmphGroupMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupMessageById(PmphGroupMessage pmphGroupMessage) throws Exception;
	
	/**
	 * @param PmphGroupMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupMessageById(PmphGroupMessage pmphGroupMessage) throws Exception;
}
