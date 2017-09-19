package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphUserMessageDao;
import com.bc.pmpheep.back.po.PmphUserMessage;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphUserMessageServiceImpl extends BaseService implements PmphUserMessageService {
	@Autowired
	private PmphUserMessageDao pmphUserMessageDao;
	
	/**
	 * 新增 一个         pmphUserMessage 
	 * @param  pmphUserMessage 
	 * @return  带主键的 PmphUserMessage
	 * @throws CheckedServiceException 
	 */
	@Override
	public PmphUserMessage addPmphUserMessage (PmphUserMessage pmphUserMessage)  throws CheckedServiceException{
		if(null==pmphUserMessage){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "参数对象为空");
		}
		if(null==pmphUserMessage.getMsgId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息为空");
		}
		if(null==pmphUserMessage.getUserId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		pmphUserMessageDao.addPmphUserMessage (pmphUserMessage);
		return pmphUserMessage;
	}
	
	/**
	 * 根据id查询 pmphUserMessage
	 * @param id
	 * @return  PmphUserMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphUserMessage getPmphUserMessageById(Long id) throws CheckedServiceException{
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphUserMessageDao.getPmphUserMessageById(id);
	}
	
	/**
	 * 根据id删除PmphUserMessage
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphUserMessageById(Long id) throws CheckedServiceException{
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphUserMessageDao.deletePmphUserMessageById(id);
	}
	
	/**
	 * 根据带主键修改 pmphUserMessage（必须带主键）
	 * @param pmphUserMessage 
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updatePmphUserMessage(PmphUserMessage pmphUserMessage) throws CheckedServiceException{
		if(null==pmphUserMessage.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphUserMessageDao.updatePmphUserMessage(pmphUserMessage);
	}

}
