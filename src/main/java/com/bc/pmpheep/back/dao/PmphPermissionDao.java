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
     <<<<<<< HEAD
     * 
     * @param PmphPermissionTest 实体对象
     * @return 带主键的PmphPermission
     * @throws Exception =======
     * @param PmphPermission 实体对象
     * @return 影响行数 >>>>>>> branch 'develop' of https://github.com/BCSquad/pmph.git
     */
    Integer addPmphPermission(PmphPermission pmphPermission);

    /**
     * 
     * @param PmphPermissionTest 必须包含主键ID
     * @return PmphPermission
     */
    PmphPermission getPmphPermissionById(PmphPermission pmphPermission);

    /**
     * 
     * @param PmphPermissionTest
     * @return 影响行数
     */
    Integer deletePmphPermissionById(PmphPermission pmphPermission);

    /**
     * @param PmphPermissionTest
     * @return 影响行数
     */
    Integer updatePmphPermissionById(PmphPermission pmphPermission);

    Integer delete(Long id);

    PmphPermission get(Long id);

    List<PmphPermission> getListResource();
    
    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
    Long getPmphPermissionCount();
}
