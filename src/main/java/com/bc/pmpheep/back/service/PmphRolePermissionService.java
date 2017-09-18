package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphRolePermissionService 接口
 * 
 * @author Mryang
 */
public interface PmphRolePermissionService {

	/**
     * 
     * @param PmphRolePermission 实体对象
     * @return 带主键的PmphRolePermission
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    PmphRolePermission addPmphRolePermission(PmphRolePermission pmphRolePermission) throws CheckedServiceException ;

    /**
     * 
     * @param id 
     * @return PmphRolePermission
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    PmphRolePermission getPmphRolePermissionById(Long id ) throws CheckedServiceException ;

    /**
     * 
     * @param id
     * @return 影响行数
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    Integer deletePmphRolePermissionById(Long id) throws CheckedServiceException ;

    /**
     * @param PmphRolePermission
     * @return 影响行数
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    Integer updatePmphRolePermission(PmphRolePermission pmphRolePermission) throws CheckedServiceException ;
}
