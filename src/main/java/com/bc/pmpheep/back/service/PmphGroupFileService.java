package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupFileService 接口
 * @author Mryang
 */
public interface PmphGroupFileService {
	
	/**
	 * 
	 * @param  PmphGroupFile 实体对象
	 * @return  带主键的 PmphGroupFile
	 * @throws  CheckedServiceException
	 */
	PmphGroupFile addPmphGroupFile (PmphGroupFile pmphGroupFile) throws  CheckedServiceException;
	
	/**
	 * 
	 * @param id 主键id
	 * @return  PmphGroupFile
	 * @throws CheckedServiceException
	 */
	PmphGroupFile getPmphGroupFileById(Long  id) throws CheckedServiceException;
	
	/**
	 * 
	 * @param id 主键id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deletePmphGroupFileById(Long  id) throws CheckedServiceException;
	
	/**
	 * 全字段更新
	 * @param pmphGroupFile
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updatePmphGroupFile(PmphGroupFile pmphGroupFile) throws CheckedServiceException;
}
