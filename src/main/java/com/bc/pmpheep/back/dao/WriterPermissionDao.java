package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.WriterPermission;

/**
 * WriterPermission实体类的数据访问层接口
 * 
 * @author 曾庆峰
 *
 */
public interface WriterPermissionDao {
    /**
     * 添加一个作家权限
     * 
     * @param writerPermission  添加的作家权限信息
     * @return 影响行数
     */
    Integer addWriterPermission(WriterPermission writerPermission);

    /**
     * 根据权限id删除权限
     * 
     * @param ids  权限Id数组
     * @return 影响行数
     */
    Integer deleteWriterPermissionById(String[] ids);

    /**
     * 根据Id 修改权限信息
     * 
     * @param writerPermission 需要修改的权限信息
     * @return 影响行数
     */
    Integer updateWriterPermissionById(WriterPermission writerPermission);

    /**
     * 根据权限名称精确查询权限
     * 
     * @param PermissionName 权限名称
     * @return 需要的权限
     */
    WriterPermission getWriterPermissionByPermissionName(String permissionName);
}
