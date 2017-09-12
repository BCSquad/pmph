package com.bc.pmpheep.back.service.orgtype;


import com.bc.pmpheep.back.po.OrgType;


/**
 * OrgTypeService 接口
 * @author Mryang
 */
public interface OrgTypeService {
	
	/**
	 * 
	 * @param OrgType 实体对象
	 * @return  影响行数
	 * @throws Exception 
	 */
	Integer addOrgType(OrgType orgType) throws Exception;
	
	/**
	 * 
	 * @param OrgType 必须包含主键ID
	 * @return  OrgType
	 * @throws Exception，NullPointerException(主键为空)
	 */
	OrgType getOrgTypeById(OrgType orgType) throws Exception;
	
	/**
	 * 
	 * @param OrgType
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deleteOrgTypeById(OrgType orgType) throws Exception;
	
	/**
	 * @param OrgType
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updateOrgTypeById(OrgType OrgType) throws Exception;
}


