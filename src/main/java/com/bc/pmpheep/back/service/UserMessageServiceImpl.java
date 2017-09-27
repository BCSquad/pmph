package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.UserMessageDao;
import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.ShiroSession;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.MessageStateVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年9月27日 下午2:53:37
 *
 **/
@Service
public class UserMessageServiceImpl extends BaseService implements UserMessageService {
	
	@Autowired
	private  UserMessageDao userMessageDao;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private WriterUserService writerUserService;
	
	@Autowired
	private OrgUserService orgUserService;

	@Override
	public Page<MessageStateVO, MessageStateVO> getMessageStateList(Page<MessageStateVO, MessageStateVO> page) throws CheckedServiceException {
		PmphUser pmphUser =ShiroSession.getPmphUser();
		if(null == pmphUser){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if(null == pmphUser.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if(null == page.getParameter().getMsgId() || "".equals(page.getParameter().getMsgId().trim())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息为空");
		}
		page.getParameter().setSenderId(pmphUser.getId());
		List<MessageStateVO>  messageStateList=userMessageDao.getMessageStateList(page);
		if(null != messageStateList && messageStateList.size() > 0 ){
			Integer count =   messageStateList.get(0).getCount();
			page.setTotal(count);
			page.setRows(messageStateList);
		}
		page.setParameter(null);
		return page;
	}
	
	@Override
	public Integer addUserMessage(Message message,Integer sendType,String orgIds,String userIds,String bookids){
		if(null == sendType || "".equals(sendType)){ 
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "参数错误!");
		}
		message=messageService.add(message);
		if(null == message.getId() || "".equals(message.getId().trim())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.OBJECT_NOT_FOUND, "储存失败!");
		}
		if(sendType == 1 || sendType == 2){//发送给学校管理员  //所有人
			if(null == orgIds || "".equals(userIds.trim())){
				throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "参数错误!");
			}
			String [] orgIds1=orgIds.split(",");
			List<Long> orgIds2=new ArrayList<Long>(orgIds1.length);
			for(String id:orgIds1){
				if(null != id && Tools.isNumeric(id)){
					orgIds2.add(Long.parseLong(id));
				}
			}
				List<WriterUser> lst1=writerUserService.getWriterUserListByOrgIds(orgIds2);
				List<OrgUser>    lst2=orgUserService.getOrgUserListByOrgIds(orgIds2);
				
		}
		
		if(sendType == 3){//指定用户
			
		}
		if(sendType == 4){//发送给教材所有报名者 
			
		}
		
		//////////do
		return null;
	}

}






