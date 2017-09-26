package com.bc.pmpheep.back.dao;


import java.util.List;

import com.bc.pmpheep.back.po.OrgType;
/**
 * OrgType 实体类数据访问层接口
 * @author mryang
 */
public interface  OrgTypeDao {
	
	/**
	 * 
	 * @param OrgType 实体对象
	 * @return  影响行数
	 */
	Integer addOrgType(OrgType orgType) ;
	
	/**
	 * 
	 * @param orgType的id如果为null查询全部 , 不为null查询对应得数据
	 * @return  List<OrgType>
	 */
	List<OrgType> getOrgType(OrgType orgType) ;
	
	/**
	 * 
	 * @param id
	 * @return  影响行数
	 */
	Integer deleteOrgTypeById(Long id) ;
	
	/**
	 * @param orgType
	 * @return 影响行数
	 */
	Integer updateOrgType(OrgType orgType) ;
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
	Long getOrgTypeCount();

}
