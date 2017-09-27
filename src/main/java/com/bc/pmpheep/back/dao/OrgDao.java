package com.bc.pmpheep.back.dao;

import java.util.List;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.vo.OrgVO;
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
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
	Long getOrgCount();
	
	/**
	 * 
	 * 获取OrgVO列表（同时查询分页数据和总条数）
	 * @author Mryang
	 * @createDate 2017年9月26日 上午10:36:10
	 * @param page
	 * @return page
	 */
	List<OrgVO> getOrgList(Page<OrgVO,OrgVO> page);
	
	
}
