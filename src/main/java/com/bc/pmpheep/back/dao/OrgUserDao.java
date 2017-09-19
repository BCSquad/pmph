package com.bc.pmpheep.back.dao;


import com.bc.pmpheep.back.po.OrgUser;

/**
 * OrgUser  实体类数据访问层接口
 * @author mryang
 */
public interface  OrgUserDao {
	/**
	 * 
	 * @param orgUser 实体对象
	 * @return   影响行数
	  */
	Integer addOrgUser(OrgUser orgUser);
	
	/**
	 * 
	 * @param id
	 * @return  OrgUser
	 */
	OrgUser getOrgUserById(Long id);
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 */
	Integer deleteOrgUserById(Long id);
	
	/**
	 * @param orgUser
	 * @return 影响行数
	 */
	Integer updateOrgUser(OrgUser orgUser) ;

}
