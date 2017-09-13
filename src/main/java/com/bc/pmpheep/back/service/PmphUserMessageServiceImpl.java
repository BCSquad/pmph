package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphUserMessageDao;
import com.bc.pmpheep.back.po.PmphUserMessage;

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
	 * 
	 * @param  PmphUserMessage 实体对象
	 * @return  带主键的 PmphUserMessage
	 * @throws Exception 
	 */
	@Override
	public PmphUserMessage addPmphUserMessage (PmphUserMessage pmphUserMessage) throws Exception{
		return pmphUserMessageDao.addPmphUserMessage (pmphUserMessage);
	}
	
	/**
	 * 
	 * @param PmphUserMessage 必须包含主键ID
	 * @return  PmphUserMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public PmphUserMessage getPmphUserMessageById(PmphUserMessage pmphUserMessage) throws Exception{
		if(null==pmphUserMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphUserMessageDao.getPmphUserMessageById(pmphUserMessage);
	}
	
	/**
	 * 
	 * @param PmphUserMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deletePmphUserMessageById(PmphUserMessage pmphUserMessage) throws Exception{
		if(null==pmphUserMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphUserMessageDao.deletePmphUserMessageById(pmphUserMessage);
	}
	
	/**
	 * @param PmphUserMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updatePmphUserMessageById(PmphUserMessage pmphUserMessage) throws Exception{
		if(null==pmphUserMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return pmphUserMessageDao.updatePmphUserMessageById(pmphUserMessage);
	}

}
