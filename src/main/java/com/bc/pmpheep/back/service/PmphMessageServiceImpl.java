package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphMessageDao;
import com.bc.pmpheep.back.po.PmphMessage;


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
	 * @throws Exception 
	 */
	@Override
	public PmphMessage addPmphMessage (PmphMessage pmphMessage) throws Exception{
		return pmphMessageDao.addPmphMessage(pmphMessage);
	}
	
	/**
	 * 
	 * @param PmphMessage 必须包含主键ID
	 * @return  PmphMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphMessage getPmphMessageById(PmphMessage pmphMessage) throws Exception{
		if(null==pmphMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphMessageDao.getPmphMessageById(pmphMessage);
	}
	
	/**
	 * 
	 * @param PmphMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphMessageById(PmphMessage pmphMessage) throws Exception{
		if(null==pmphMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphMessageDao.deletePmphMessageById(pmphMessage);
	}
	
	/**
	 * @param PmphMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphMessageById(PmphMessage pmphMessage) throws Exception{
		if(null==pmphMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphMessageDao.updatePmphMessageById(pmphMessage);
	}
}
