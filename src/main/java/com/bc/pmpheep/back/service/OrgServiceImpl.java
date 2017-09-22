package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgDao;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgService 接口实现
 * @author Mryang
 *
 */
@Service
public class OrgServiceImpl extends BaseService implements OrgService {
	
	@Autowired
	private OrgDao orgDao;
	
	/**
	 * 
	 * @param org 实体对象
	 * @return  Org 带主键
	 * @throws CheckedServiceException 
	 */
	@Override
	public Org addOrg(Org org) throws CheckedServiceException{
		if(null==org.getOrgName()){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "机构名称为空");
		}
		Long id = org.getId();
		orgDao.addOrg(org);
		if(null != id){
			org.setId(id);
		}
		return org;
	}
	
	/**
	 * 
	 * @param id
	 * @return  Org
	 * @throws CheckedServiceException
	 */
	@Override
	public Org getOrgById(Long  id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgDao.getOrgById(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgById(Long  id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgDao.deleteOrgById(id);
	}
	
	/**
	 * @param org
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updateOrg(Org org) throws CheckedServiceException{
		if(null==org.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgDao.updateOrg(org);
	}

}
