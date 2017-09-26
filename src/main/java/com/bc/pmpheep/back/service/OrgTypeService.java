package com.bc.pmpheep.back.service;


import java.util.List;

import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * OrgTypeService 接口
 * @author Mryang
 */
public interface OrgTypeService {
	
	/**
	 * 
	 * @param OrgType 实体对象
	 * @return  带主键的OrgType
	 * @throws CheckedServiceException 
	 */
	OrgType addOrgType(OrgType orgType) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id 如果为null 查询全部  不为null查询对应得数据
	 * @return  List<OrgType>
	 * @throws CheckedServiceException
	 */
	List<OrgType> getOrgType(Long id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgTypeById(Long id) throws CheckedServiceException;
	
	/**
	 * @param orgType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrgType(OrgType orgType) throws CheckedServiceException;
}


