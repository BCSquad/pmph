package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterPermission;

/**
 * WriterPermissionService 接口
 * @author 曾庆峰
 *
 */
public interface WriterPermissionService {
    /**
     * 
     * @param writerPermission 添加的作家权限
     * @return 影响行数
     * @throws Exception
     */
    Integer addWriterPermission(WriterPermission writerPermission) throws Exception;

    /**
     * 
     * @param ids 需要删除权限的id数组
     * @return 影响行数
     * @throws Exception
     */
    Integer deleteWriterPermissionById(String[] ids) throws Exception;

    /**
     * 
     * @param writerPermission 修改的权限信息
     * @return 影响行数
     * @throws Exception
     */
    Integer updateWriterPermissionById(WriterPermission writerPermission) throws Exception;

    /**
     * 
     * @param permissionName 作家权限名称
     * @return 需要的作家权限信息
     * @throws Exception
     */
    WriterPermission getWriterPermissionByPermissionName(String permissionName) throws Exception;
}
