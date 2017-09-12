package com.bc.pmpheep.back.dao.orgtype;

import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.OrgType;
/**
 * OrgTypeDao实体类数据访问层接口
 * @author mryang
 */
@Component
public interface  OrgTypeDao {
	
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
	OrgType getOrgTypeDaoById(OrgType orgType) throws Exception;
	
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
