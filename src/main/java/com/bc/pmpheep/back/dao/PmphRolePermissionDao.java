package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphRolePermission;

/**
 * PmphRolePermission 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface  PmphRolePermissionDao {
	/**
     * 
     * @param pmphRolePermission 实体对象
     * @return 影响行数
     */
	Integer addPmphRolePermission(PmphRolePermission pmphRolePermission) ;

    /**
     * 
     * @param id 
     * @return PmphRolePermission
     */
    PmphRolePermission getPmphRolePermissionById(Long id ) ;

    /**
     * 
     * @param id
     * @return 影响行数
     */
    Integer deletePmphRolePermissionById(Long id) ;

    /**
     * @param pmphRolePermission
     * @return 影响行数
     */
    Integer updatePmphRolePermission(PmphRolePermission pmphRolePermission) ;
    
    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphRolePermissionCount();
}
