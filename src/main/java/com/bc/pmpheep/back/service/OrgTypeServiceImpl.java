package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgTypeDao;
import com.bc.pmpheep.back.po.OrgType;

/**
 * OrgTypeService 接口实现
 * @author Mryang
 *
 */
@Service
public class OrgTypeServiceImpl extends BaseService implements OrgTypeService {
	@Autowired
	private OrgTypeDao orgTypeDao;
	
	/**
	 * 
	 * @param OrgType 实体对象
	 * @return  带主键的OrgType
	 * @throws Exception 
	 */
	@Override
	public OrgType addOrgType(OrgType orgType) throws Exception{
		orgTypeDao.addOrgType(orgType);
		return orgType;
	}
	
	/**
	 * 
	 * @param OrgType 必须包含主键ID
	 * @return  OrgType
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public OrgType getOrgTypeById(OrgType orgType) throws Exception{
		if(null==orgType.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgTypeDao.getOrgTypeById(orgType);
	}
	
	/**
	 * 
	 * @param area
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteOrgTypeById(OrgType orgType) throws Exception{
		if(null==orgType.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgTypeDao.deleteOrgTypeById(orgType);
	}
	
	/**
	 * @param area
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateOrgTypeById(OrgType orgType) throws Exception{
		if(null==orgType.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgTypeDao.updateOrgTypeById(orgType);
	}
	

}
