package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphDepartment;

/**
 * PmphDepartmentService 接口
 * @author Mryang
 */
public interface PmphDepartmentService {
	/**
	 * 
	 * @param  PmphDepartment 实体对象
	 * @return  PmphDepartment 主键
	 * @throws Exception 
	 */
	PmphDepartment addpmphDepartment(PmphDepartment pmphDepartment) throws Exception;
	
	/**
	 * 
	 * @param PmphDepartment 必须包含主键ID
	 * @return  PmphDepartment
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphDepartment getPmphDepartmentById(PmphDepartment pmphDepartment) throws Exception;
	
	/**
	 * 
	 * @param PmphDepartment
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphDepartmentById(PmphDepartment pmphDepartment) throws Exception;
	
	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphDepartmentById(PmphDepartment pmphDepartment) throws Exception;

	
}

