package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphMessageDao;
import com.bc.pmpheep.back.po.PmphMessage;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * BaseService 接口实现
 * @author Mryang
 *
 */
@Service
public class PmphMessageServiceImpl extends BaseService implements PmphMessageService {
	@Autowired
	private PmphMessageDao pmphMessageDao;
	
	/**
	 * 
	 * @param  PmphMessage 实体对象
	 * @return  带主键的PmphMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphMessage addPmphMessage (PmphMessage pmphMessage) throws CheckedServiceException{
		if(Tools.isEmpty(pmphMessage.getMsgCode())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息标识空不允许新增");
		}
		pmphMessageDao.addPmphMessage(pmphMessage);
		return pmphMessage;
	}
	
	/**
	 * 
	 * @param id
	 * @return  PmphMessage
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphMessage getPmphMessageById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphMessageDao.getPmphMessageById(id);
	}
	
	/**
	 * 
	 * @param PmphMessage
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphMessageById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return pmphMessageDao.deletePmphMessageById(id);
	}
	
	/**
	 * @param pmphMessage
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updatePmphMessage(PmphMessage pmphMessage) throws CheckedServiceException{
		if(null==pmphMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphMessageDao.updatePmphMessage(pmphMessage);
	}
}
