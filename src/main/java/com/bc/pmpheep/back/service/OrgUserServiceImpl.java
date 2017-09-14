package com.bc.pmpheep.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.OrgUserDao;
import com.bc.pmpheep.back.po.OrgUser;

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
	 * @param OrgUser 实体对象
	 * @return   带主键的 OrgUser
	 * @throws Exception 
	 */
	@Override
	public OrgUser addOrgUser(OrgUser orgUser) throws Exception{
		orgUserDao.addOrgUser(orgUser);
		return orgUser;
	}
	
	/**
	 * 
	 * @param OrgUser 必须包含主键ID
	 * @return  OrgUser
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public OrgUser getOrgUserById(OrgUser orgUser) throws Exception{
		if(null==orgUser.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgUserDao.getOrgUserById(orgUser);
	}
	
	/**
	 * 
	 * @param OrgUser
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	@Override
	public Integer deleteOrgUserById(OrgUser orgUser) throws Exception{
		if(null==orgUser.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgUserDao.deleteOrgUserById(orgUser);
	}
	
	/**
	 * @param OrgUser
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	@Override 
	public Integer updateOrgUserById(OrgUser orgUser) throws Exception{
		if(null==orgUser.getId()){
			throw new NullPointerException("主键id为空");
		}
		return orgUserDao.updateOrgUserById(orgUser);
	}
	
}
