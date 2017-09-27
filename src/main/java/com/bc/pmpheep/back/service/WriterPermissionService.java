package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterPermissionService 接口
 * 
 * @author 曾庆峰
 * 
 */
public interface WriterPermissionService {
    /**
     * 
     * @param writerPermission 添加的作家权限
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer addWriterPermission(WriterPermission writerPermission) throws CheckedServiceException;

    /**
     * 
     * @param ids 需要删除权限的id数组
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer deleteWriterPermissionById(String[] ids) throws CheckedServiceException;

    /**
     * 
     * @param writerPermission 修改的权限信息
     * @return 影响行数
     * @throws CheckedServiceException
     */
    Integer updateWriterPermissionById(WriterPermission writerPermission)
    throws CheckedServiceException;

    /**
     * 
     * @param permissionName 作家权限名称
     * @return 需要的作家权限信息
     * @throws CheckedServiceException
     */
    WriterPermission getWriterPermissionByPermissionName(String permissionName)
    throws CheckedServiceException;

    /**
     * 按WriterPermission_id删除 对象
     * 
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    Integer delete(Long id) throws CheckedServiceException;

    /**
     * 按WriterPermission_id查询 对象
     * 
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    WriterPermission get(Long id) throws CheckedServiceException;

    /**
     * 查询List对象集合
     * 
     * @param id
     * @return
     * @throws CheckedServiceException
     */
    List<WriterPermission> getListResource() throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取所有父级节点
     * 使用示范：
     *
     * @return
     * </pre>
     */
    List<WriterPermission> getListAllParentMenu();

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
    List<WriterPermission> getListChildMenuByParentId(Long parentId);
}
