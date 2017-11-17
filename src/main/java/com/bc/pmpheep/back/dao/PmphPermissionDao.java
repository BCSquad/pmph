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
     * 
     * @param PmphPermission 实体对象
     * @return 影响行数 
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

    /**
     * 
     * <pre>
     * 功能描述：按ID 删除
     * 使用示范：
     *
     * @param id
     * @return
     * </pre>
     */
    Integer delete(Long id);

    /**
     * 
     * <pre>
     * 功能描述：按ID查询对象
     * 使用示范：
     *
     * @param id
     * @return
     * </pre>
     */
    PmphPermission get(Long id);

    /**
     * 
     * <pre>
     * 功能描述：获取所有对象
     * 使用示范：
     *
     * @return
     * </pre>
     */
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

    /**
     * 
     * <pre>
     * 功能描述：获取所有父级节点
     * 使用示范：
     *
     * @return
     * </pre>
     */
    List<PmphPermission> getListAllParentMenu();

    /**
     * 
     * <pre>
     * 功能描述：按父节点ID查询对应子节点
     * 使用示范：
     *
     * @param parentId
     * @return
     * </pre>
     */
    List<PmphPermission> getListChildMenuByParentId(Long parentId);

}
