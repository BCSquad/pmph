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
	 * @return 影响行数
	 */
	Integer addPmphDepartment(PmphDepartment pmphDepartment) ;
	
	/**
	 * 
	 * @param id
	 * @return  PmphDepartment
	  */
	PmphDepartment getPmphDepartmentById(Long id)  ;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	  */
	Integer deletePmphDepartmentById(Long id)  ;
	
	/**
	 * @param PmphDepartment
	 * @return 影响行数
	 */
	Integer updatePmphDepartment(PmphDepartment pmphDepartment)  ;
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphDepartmentCount();

}
