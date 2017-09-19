package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * WriterRolePermissionService 接口
 * 
 * @author 曾庆峰 <791038935@qq.com>
 *
 */
public interface WriterRolePermissionService {

	/**
	 * 新增一个WriterRolePermission
	 * 
	 * @param WriterRolePermission
	 *            实体对象
	 * @return 带主键的 WriterRolePermission thorws CheckedServiceException
	 */
	WriterRolePermission addWriterRolePermission(WriterRolePermission writerRolePermission)
			throws CheckedServiceException;

	/**
	 * 查询一个 WriterRolePermission 通过主键id
	 * 
	 * @param id
	 * @return WriterRolePermission
	 * @throws CheckedServiceException
	 */
	WriterRolePermission getWriterRolePermissionById(Long id) throws CheckedServiceException;

	/**
	 * 删除WriterRolePermission 通过主键id
	 * 
	 * @param WriterRolePermission
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteWriterRolePermissionById(Long id) throws CheckedServiceException;

	/**
	 * 更新一个 WriterRolePermission通过主键id
	 * 
	 * @param WriterRolePermission
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateWriterRolePermission(WriterRolePermission writerRolePermission) throws CheckedServiceException;

}
