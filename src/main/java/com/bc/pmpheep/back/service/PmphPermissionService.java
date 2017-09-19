package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.Page;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphPermissionService 接口
 * 
 * @author Mryang
 */
public interface PmphPermissionService {
    /**
     * 
     * @param PmphPermissionTest 实体对象
     * @return 带主键的PmphPermission
     * @throws CheckedServiceException
     */
    PmphPermission addPmphPermission(PmphPermission pmphPermission) throws CheckedServiceException;

    /**
     * 
     * @param PmphPermissionTest 必须包含主键ID
     * @return PmphPermission
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    PmphPermission getPmphPermissionById(PmphPermission pmphPermission)
    throws CheckedServiceException;

    /**
     * 
     * @param PmphPermissionTest
     * @return 影响行数
     * @throws CheckedServiceException，NullPointerException(主键为空)
     */
    Integer deletePmphPermissionById(PmphPermission pmphPermission) throws CheckedServiceException;

    /**
     * @param PmphPermissionTest
     * @return 影响行数
     * @throws CheckedServiceException ，NullPointerException(主键为空)
     */
    Integer updatePmphPermissionById(PmphPermission pmphPermission) throws CheckedServiceException;

    /**
     * 按PmphPermission_id删除 对象
     * 
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    Integer delete(Long id) throws CheckedServiceException;

    /**
     * 按PmphPermission_id查询 对象
     * 
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    PmphPermission get(Long id) throws CheckedServiceException;

    /**
     * 查询List对象集合
     * 
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    List<PmphPermission> getListResource() throws CheckedServiceException;

    /**
     * 分页
     * 
     * @param page
     * @return
     * @throws CheckedServiceException
     */
    List<PmphPermission> getListPageResource(Page page) throws CheckedServiceException;
}
