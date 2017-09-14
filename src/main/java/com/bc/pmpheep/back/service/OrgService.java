package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.Org;


/**
 * OrgService 接口
 * @author Mryang
 *
 */
public interface OrgService {
	
	/**
	 * 
	 * @param org 实体对象
	 * @return  带主键的Org 
	 */
	Org addOrg(Org org) throws Exception ;
	
	/**
	 * 
	 * @param org 必须包含主键ID
	 * @return  area
	 */
	Org getOrgById(Org org)  throws Exception;
	
	/**
	 * 
	 * @param org
	 * @return  影响行数
	 */
	Integer deleteOrgById(Org org)  throws Exception;
	
	/**
	 * @param org
	 * @return 影响行数
	 */
	Integer updateOrgById(Org org)  throws Exception;
	
	
}
