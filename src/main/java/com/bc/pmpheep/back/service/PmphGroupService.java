package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口
 * @author Mryang
 */
public interface  PmphGroupService {
	/**
	 * 
	 * @param  pmphGroup 实体对象
	 * @return  带主键的PmphGroup
	 * @throws CheckedServiceException 
	 */
	PmphGroup addPmphGroup (PmphGroup pmphGroup) throws  CheckedServiceException;
	
	/**
	 * 
	 * @param id 主键ID
	 * @return  PmphGroup
	 * @throws CheckedServiceException
	 */
	PmphGroup getPmphGroupById(Long  id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id 主键ID
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphGroupById(Long  id) throws CheckedServiceException;
	
	/**
	 * PmphGroup全字段更新
	 * @param pmphGroup 必须包含主键
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroup(PmphGroup pmphGroup) throws CheckedServiceException;
}
