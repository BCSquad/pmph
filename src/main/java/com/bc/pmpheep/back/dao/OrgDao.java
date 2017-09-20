package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.service.exception.CheckedServiceException;


/**
 * OrgDao实体类数据访问层接口
 * @author mryang
 */
public interface OrgDao {
	
	/**
	 * 
	 * @param org 实体对象
	 * @return  影像行数
	 * @throws CheckedServiceException 
	 */
	Integer addOrg(Org org) ;
	
	/**
	 * 
	 * @param id
	 * @return  Org
	 * @throws CheckedServiceException
	 */
	Org getOrgById(Long  id) ;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteOrgById(Long  id) ;
	
	/**
	 * @param org
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateOrg(Org org) ;
	/**
	 * 
	 * <pre>
	 * 功能描述：根据机构名称查询机构
	 * 使用示范：
	 *
	 * @param orgName  机构名称
	 * @return 机构名称与机构id
	 * </pre>
	 */
	List<Org> getListOrgByOrgName(String orgName);
	
	
}
