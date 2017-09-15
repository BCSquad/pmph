package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroup;

/**
 * PmphGroup 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphGroupDao {
	/**
	 * 
	 * @param  pmphGroup 实体对象
	 * @return   影响行数
	 */
	Integer addPmphGroup (PmphGroup pmphGroup) ;
	
	/**
	 * 
	 * @param id 主键ID
	 * @return  PmphGroup
	 */
	PmphGroup getPmphGroupById(Long  id) ;
	
	/**
	 * 
	 * @param id 主键ID
	 * @return  影响行数
	 */
	Integer deletePmphGroupById(Long  id) ;
	
	/**
	 * PmphGroup全字段更新
	 * @param pmphGroup 必须包含主键
	 * @return 影响行数
	 */
	Integer updatePmphGroup(PmphGroup pmphGroup) ;
}
