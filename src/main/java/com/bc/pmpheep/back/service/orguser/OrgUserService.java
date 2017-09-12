package com.bc.pmpheep.back.service.orguser;

import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.po.OrgUser;

/**
 * OrgUserService 接口
 * @author Mryang
 */
public interface  OrgUserService {
	/**
	 * 
	 * @param OrgUser 实体对象
	 * @return  影响行数
	 * @throws Exception 
	 */
	Integer addOrgUser(OrgUser orgUser) throws Exception;
	
	/**
	 * 
	 * @param OrgUser 必须包含主键ID
	 * @return  OrgUser
	 * @throws Exception，NullPointerException(主键为空)
	 */
	OrgType getOrgUserById(OrgUser orgUser) throws Exception;
	
	/**
	 * 
	 * @param OrgUser
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteOrgUserById(OrgUser orgUser) throws Exception;
	
	/**
	 * @param OrgUser
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateOrgUserById(OrgUser orgUser) throws Exception;
}
