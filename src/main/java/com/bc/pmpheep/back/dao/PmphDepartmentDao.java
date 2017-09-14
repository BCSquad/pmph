package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphDepartment;

/**
 * PmphDepartment 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphDepartmentDao {
	/**
	 * 
	 * @param  PmphDepartment 实体对象
	 * @return  影响行数
	 * @throws Exception 
	 */
	Integer addPmphDepartment(PmphDepartment pmphDepartment);
	
	/**
	 * 
	 * @param PmphDepartment 必须包含主键ID
	 * @return  PmphDepartment
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphDepartment getPmphDepartmentById(PmphDepartment pmphDepartment);
	
	/**
	 * 
	 * @param PmphDepartment
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphDepartmentById(PmphDepartment pmphDepartment);
	
	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphDepartmentById(PmphDepartment pmphDepartment) ;

}
