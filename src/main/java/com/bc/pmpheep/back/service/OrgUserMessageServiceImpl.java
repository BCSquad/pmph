package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgUserMessageDao;
import com.bc.pmpheep.back.po.OrgUserMessage;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * PmphGroupService 接口实现
 * @author Mryang
 *
 */
@Service
public class OrgUserMessageServiceImpl extends BaseService implements OrgUserMessageService {
	@Autowired
	private OrgUserMessageDao orgUserMessgeDao;
	
	/**
	 * 
	 * @param  orgUserMessage 实体对象
	 * @return  带主键的 OrgUserMessage
	 * @throws CheckedServiceException 
	 */
	@Override
	public OrgUserMessage addOrgUserMessage (OrgUserMessage orgUserMessage) throws CheckedServiceException{
		orgUserMessgeDao.addOrgUserMessage(orgUserMessage);
		return orgUserMessage;
	}
	
	/**
	 * 
	 * @param id
	 * @return  OrgUserMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgUserMessage getOrgUserMessageById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserMessgeDao.getOrgUserMessageById(id);
	}
	
	/**
	 * 
	 * @param OrgUserMessage
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgUserMessageById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserMessgeDao.deleteOrgUserMessageById(id);
	}
	
	/**
	 * @param OrgUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateOrgUserMessage(OrgUserMessage orgUserMessage) throws Exception{
		if(null==orgUserMessage.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserMessgeDao.updateOrgUserMessage(orgUserMessage);
	}
}
