package com.bc.pmpheep.back.dao.org;

import org.springframework.stereotype.Component;
import com.bc.pmpheep.back.po.Org;


/**
 * OrgDao实体类数据访问层接口
 * @author mryang
 */
@Component
public interface OrgDao {
	
	/**
	 * 
	 * @param org 实体对象
	 * @return  影响行数
	 */
	Integer addOrg(Org org) ;
	
	/**
	 * 
	 * @param org 必须包含主键ID
	 * @return  area
	 */
	Org getOrgById(Org org);
	
	/**
	 * 
	 * @param org
	 * @return  影响行数
	 */
	Integer deleteOrgById(Org org);
	
	/**
	 * @param org
	 * @return 影响行数
	 */
	Integer updateOrgById(Org org) ;
	
	
}
