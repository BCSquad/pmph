package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.PmphPermission;

/**
 * PmphPermission 实体类数据访问层接口
 * 
 * @author mryang
 */
@Repository
public interface PmphPermissionDao {
    /**
     * 
     * @param PmphPermission 实体对象
     * @return 带主键的PmphPermission
     * @throws Exception
     */
    PmphPermission addPmphPermission(PmphPermission pmphPermission);

    /**
     * 
     * @param PmphPermission 必须包含主键ID
     * @return PmphPermission
     * @throws Exception，NullPointerException(主键为空)
     */
    PmphPermission getPmphPermissionById(PmphPermission pmphPermission);

    /**
     * 
     * @param PmphPermission
     * @return 影响行数
     * @throws Exception，NullPointerException(主键为空)
     */
    Integer deletePmphPermissionById(PmphPermission pmphPermission);

    /**
     * @param PmphPermission
     * @return 影响行数
     * @throws Exception ，NullPointerException(主键为空)
     */
    Integer updatePmphPermissionById(PmphPermission pmphPermission);

    Integer delete(int id);

    PmphPermission get(int id);

    List<PmphPermission> getListResource();

}
