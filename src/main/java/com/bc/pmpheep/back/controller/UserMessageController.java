package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.back.plugin.Page;
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
	
	@RequestMapping(value = "/addUserMessage")
    @ResponseBody
    public ResponseBean addUserMessage(Message message,Integer sendType,String orgIds,String userIds,String bookids){
		return new ResponseBean(userMessageService.addUserMessage(message,sendType,orgIds,userIds,bookids));
	}

}
