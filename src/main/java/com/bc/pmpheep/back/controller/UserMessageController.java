package com.bc.pmpheep.back.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Message;

/**
 *@author MrYang 
 *@CreateDate 2017年9月27日 下午2:50:12
 *
 **/
@Controller
@RequestMapping(value = "/userMessage")
@SuppressWarnings({"rawtypes","unchecked"})
public class UserMessageController {
	
	@Autowired
	private UserMessageService userMessageService;
	
	/**
	 * 分页查询条件查询MessageState 列表
	 * @author Mryang
	 * @createDate 2017年9月26日 上午9:46:19
	 * @return 分页数据集
	 */
	@RequestMapping(value = "/getMessageStateList")
    @ResponseBody
    public ResponseBean getMessageStateList(MessageStateVO messageStateVO,Page page) {
		page.setParameter(messageStateVO);
        return new ResponseBean(userMessageService.getMessageStateList(page));
    }
	
	/**
	 *  向各个对象发送消息 
	 * @author Mryang
	 * @createDate 2017年9月28日 下午4:29:27
	 * @param message
	 * @param sendType  //1 发送给学校管理员  //2 所有人  //3指定用户 //4发送给教材所有报名者
	 * @param orgIds
	 * @param userIds
	 * @param bookids
	 * @return
	 */
	@RequestMapping(value = "/addUserMessage")
    @ResponseBody
    public ResponseBean addUserMessage(Message message,Integer sendType,String orgIds,String userIds,String bookids){
		try {
			return new ResponseBean(userMessageService.addOrUpdateUserMessage(message,sendType,orgIds,userIds,bookids,true));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}
	
	/**
	 * 向各个对象补发消息 
	 * @author Mryang
	 * @createDate 2017年9月28日 下午4:29:27
	 * @param message
	 * @param sendType  //1 发送给学校管理员  //2 所有人  //3指定用户 //4发送给教材所有报名者
	 * @param orgIds
	 * @param userIds
	 * @param bookids
	 * @return
	 */
	@RequestMapping(value = "/addUserMessageAgain")
    @ResponseBody
    public ResponseBean addUserMessageAgain(Message message,Integer sendType,String orgIds,String userIds,String bookIds){
		try {
			return new ResponseBean(userMessageService.addOrUpdateUserMessage(message,sendType,orgIds,userIds,bookIds,false));
		} catch (IOException e) {
			return new ResponseBean(e);
		}
	}
	
	/**
	 * 单纯修改消息 
	 * @author Mryang
	 * @createDate 2017年9月29日 下午4:30:14
	 * @param message
	 * @return 影响行数
	 */
	@RequestMapping(value = "/updateUserMessage")
    @ResponseBody
    public ResponseBean updateUserMessage(Message message){
		return new ResponseBean(userMessageService.updateUserMessage(message));
	}
	
	/**
	 * 撤回消息
	 * @author Mryang
	 * @createDate 2017年9月29日 下午5:18:20
	 * @param userMessage
	 * @return
	 */
	@RequestMapping(value = "/withdrawUserMessage")
    @ResponseBody
	public ResponseBean withdrawUserMessage(UserMessage userMessage){
		return new ResponseBean(userMessageService.updateToWithdraw(new UserMessage(userMessage.getMsgId(),true)));
	}
	
	/**
	 * 通过消息id删除UserMessage  
	 */
	@RequestMapping(value = "/deleteUserMessage")
    @ResponseBody
	public ResponseBean deleteUserMessage(UserMessage userMessage){
		return new ResponseBean(userMessageService.deleteMessageByMsgId(userMessage.getMsgId()));
	}
	
	
	
}













