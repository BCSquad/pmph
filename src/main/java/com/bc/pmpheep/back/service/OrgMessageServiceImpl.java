package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgMessageDao;
import com.bc.pmpheep.back.po.OrgMessage;



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
	 * @param  OrgMessage 实体对象
	 * @return  带主键的OrgMessage
	 * @throws Exception 
	 */
	@Override
	public OrgMessage addOrgMessage(OrgMessage orgMessage) throws Exception{
		orgMessageDao.addOrgMessage(orgMessage);
		return orgMessage;
	}
	
	/**
	 * 
	 * @param OrgMessage 必须包含主键ID
	 * @return  OrgMessage
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public OrgMessage getOrgMessageById(OrgMessage orgMessage) throws Exception{
		if(null==orgMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgMessageDao.getOrgMessageById(orgMessage);
	}
	
	/**
	 * 
	 * @param OrgMessage
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteOrgMessageById(OrgMessage orgMessage) throws Exception{
		if(null==orgMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgMessageDao.deleteOrgMessageById(orgMessage);
	}
	
	/**
	 * @param OrgMessage
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateOrgMessageById(OrgMessage orgMessage) throws Exception{
		if(null==orgMessage.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgMessageDao.updateOrgMessageById(orgMessage);
	}
}
