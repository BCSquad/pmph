package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphGroup 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphGroupDao {
	/**
	 * 
	 * @param  PmphGroup 实体对象
	 * @return  带主键的PmphGroup
	 * @throws Exception 
	 */
	PmphPermission addPmphGroup (PmphGroup pmphGroup) ;
	
	/**
	 * 
	 * @param PmphGroup 必须包含主键ID
	 * @return  PmphGroup
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphPermission getPmphGroupById(PmphGroup pmphGroup) ;
	
	/**
	 * 
	 * @param PmphGroup
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupById(PmphGroup pmphGroup) ;
	
	/**
	 * @param PmphGroup
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupById(PmphGroup pmphGroup) ;
}
