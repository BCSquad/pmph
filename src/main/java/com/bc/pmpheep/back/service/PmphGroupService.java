package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphGroupService 接口
 * @author Mryang
 */
public interface  PmphGroupService {
	/**
	 * 
	 * @param  PmphGroup 实体对象
	 * @return  带主键的PmphGroup
	 * @throws Exception 
	 */
	PmphGroup addPmphGroup (PmphGroup pmphGroup) throws Exception;
	
	/**
	 * 
	 * @param PmphGroup 必须包含主键ID
	 * @return  PmphGroup
	 * @throws Exception，NullPointerException(主键为空)
	 */
	PmphGroup getPmphGroupById(PmphGroup pmphGroup) throws Exception;
	
	/**
	 * 
	 * @param PmphGroup
	 * @return  影响行数
	 * @throws Exception，NullPointerException(主键为空)
	 */
	Integer deletePmphGroupById(PmphGroup pmphGroup) throws Exception;
	
	/**
	 * @param PmphGroup
	 * @return 影响行数
	 * @throws Exception ，NullPointerException(主键为空)
	 */
	Integer updatePmphGroupById(PmphGroup pmphGroup) throws Exception;
}
