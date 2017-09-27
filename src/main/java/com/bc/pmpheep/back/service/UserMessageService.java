package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年9月27日 下午2:51:25
 *
 **/
public interface UserMessageService {
	
	/**
	 * @param  page 带有分页参数和查询条件参数
	 * @return Page<MessageStateVO,MessageStateVO>  包含 List<MessageStateVO>以及分页数据
	 * @throws CheckedServiceException
	 */
	Page<MessageStateVO,MessageStateVO> getMessageStateList(Page<MessageStateVO,MessageStateVO> page);
	
	Integer addUserMessage(Message message,Integer sendType,String orgIds,String userIds,String bookids);
}
