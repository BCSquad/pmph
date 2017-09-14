package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphPermissionService 接口
 * 
 * @author Mryang
 */
public interface PmphPermissionService {
    /**
     * 
     * @param PmphPermission 实体对象
     * @return 带主键的PmphPermission
     * @throws Exception
     */
    PmphPermission addPmphPermission(PmphPermission pmphPermission) throws Exception;

    /**
     * 
     * @param PmphPermission 必须包含主键ID
     * @return PmphPermission
     * @throws Exception，NullPointerException(主键为空)
     */
    PmphPermission getPmphPermissionById(PmphPermission pmphPermission) throws Exception;

    /**
     * 
     * @param PmphPermission
     * @return 影响行数
     * @throws Exception，NullPointerException(主键为空)
     */
    Integer deletePmphPermissionById(PmphPermission pmphPermission) throws Exception;

    /**
     * @param PmphPermission
     * @return 影响行数
     * @throws Exception ，NullPointerException(主键为空)
     */
    Integer updatePmphPermissionById(PmphPermission pmphPermission) throws Exception;

    /**
     * 按PmphPermission_id删除 对象
     * 
     * @param id
     * @return
     * @throws Exception
     */
    Integer delete(int id) throws Exception;

    /**
     * 按PmphPermission_id查询 对象
     * 
     * @param id
     * @return
     * @throws Exception
     */
    PmphPermission get(int id) throws Exception;

    /**
     * 查询List对象集合
     * 
     * @param id
     * @return
     * @throws Exception
     */
    List<PmphPermission> getListResource() throws Exception;
}
