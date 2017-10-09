package com.bc.pmpheep.back.service;

import java.io.IOException;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.UserMessage;
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
	 * @param  pageParameter 带有分页参数和查询条件参数
	 * @return PageResult<MessageStateVO>  包含 List<MessageStateVO>以及分页数据
	 * @throws CheckedServiceException
	 */
	PageResult<MessageStateVO> getMessageStateList(PageParameter<MessageStateVO> pageParameter) throws CheckedServiceException;
	
	/**
	 *  向各个对象发送消息 
	 * @author Mryang
	 * @createDate 2017年9月28日 下午4:29:27
	 * @param message
	 * @param sendType  //1 发送给学校管理员  //2 所有人  //3指定用户 //4发送给教材所有报名者
	 * @param orgIds
	 * @param userIds
	 * @param bookids
	 * @param isSave true新增，false补发
	 * @return
	 */
	Integer addOrUpdateUserMessage(Message message,Integer sendType,String orgIds,String userIds,String bookIds,boolean isSave) throws CheckedServiceException,IOException;
	
	/**
	 * 单纯修改消息 
	 * @author Mryang
	 * @createDate 2017年9月29日 下午4:30:14
	 * @param message
	 * @return 影响行数
	 */
	Integer updateUserMessage(Message message) throws CheckedServiceException;
	
	/**
	 * 撤回消息
	 * @author Mryang
	 * @createDate 2017年9月29日 下午4:44:35
	 * @param userMessage
	 * @return
	 */
	Integer updateToWithdraw(UserMessage userMessage) throws CheckedServiceException;
	
	/**
	 * 通过消息id删除UserMessage
	 * @return 影响行数
	 */
	Integer  deleteMessageByMsgId (String msgId) throws CheckedServiceException;
}
