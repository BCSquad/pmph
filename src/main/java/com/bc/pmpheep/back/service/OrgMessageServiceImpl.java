package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgMessageDao;
import com.bc.pmpheep.back.po.OrgMessage;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;



/**
 * OrgMessageService 接口实现
 * @author Mryang
 *
 */
@Service
public class OrgMessageServiceImpl extends BaseService  implements OrgMessageService {
	@Autowired
	private OrgMessageDao orgMessageDao;
	
	/**
	 * 
	 * @param  orgMessage 实体对象
	 * @return  带主键的OrgMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgMessage addOrgMessage(OrgMessage orgMessage) throws CheckedServiceException{
		if(Tools.isEmpty(orgMessage.getMsgCode())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息标识为空");
		}
		orgMessageDao.addOrgMessage(orgMessage);
		return orgMessage;
	}
	
	/**
	 * 
	 * @param id
	 * @return  OrgMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgMessage getOrgMessageById(Long  id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgMessageDao.getOrgMessageById(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgMessageById(Long  id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgMessageDao.deleteOrgMessageById(id);
	}
	
	/**
	 * @param orgMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updateOrgMessage(OrgMessage orgMessage) throws CheckedServiceException{
		if(null==orgMessage.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgMessageDao.updateOrgMessage(orgMessage);
	}
}
