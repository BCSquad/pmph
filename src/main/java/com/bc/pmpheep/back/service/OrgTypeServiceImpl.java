package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgTypeDao;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	 * @throws CheckedServiceException 
	 */
	@Override
	public OrgType addOrgType(OrgType orgType) throws CheckedServiceException{
		if(null==orgType.getTypeName()){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "机构类型名称为空");
		}
		orgTypeDao.addOrgType(orgType);
		return orgType;
	}
	
	/**
	 * 
	 * @param OrgType 必须包含主键ID
	 * @return  OrgType
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgType getOrgTypeById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgTypeDao.getOrgTypeById(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgTypeById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgTypeDao.deleteOrgTypeById(id);
	}
	
	/**
	 * @param orgType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updateOrgType(OrgType orgType) throws CheckedServiceException{
		if(null==orgType.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.ORG, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgTypeDao.updateOrgType(orgType);
	}
	

}
