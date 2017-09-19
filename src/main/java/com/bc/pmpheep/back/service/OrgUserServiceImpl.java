package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgUserDao;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * OrgUserServiceImpl 接口实现
 * @author Mryang
 *
 */
@Service
public class OrgUserServiceImpl extends BaseService implements OrgUserService {
	@Autowired
	private OrgUserDao orgUserDao;
	
	/**
	 * 
	 * @param orgUser 实体对象
	 * @return   带主键的 OrgUser
	 * @throws CheckedServiceException 
	 */
	@Override
	public OrgUser addOrgUser(OrgUser orgUser) throws CheckedServiceException{
		if(null==orgUser.getRealname()){
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "真实名称为空");
		}
		orgUserDao.addOrgUser(orgUser);
		return orgUser;
	}
	
	/**
	 * 
	 * @param id
	 * @return  OrgUser
	 * @throws CheckedServiceException
	 */
	@Override
	public OrgUser getOrgUserById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserDao.getOrgUserById(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deleteOrgUserById(Long id) throws CheckedServiceException{
		if(null==id){
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserDao.deleteOrgUserById(id);
	}
	
	/**
	 * @param orgUser
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override 
	public Integer updateOrgUser(OrgUser orgUser) throws CheckedServiceException{
		if(null==orgUser.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.USER_MANAGEMENT, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return orgUserDao.updateOrgUser(orgUser);
	}
	
}
