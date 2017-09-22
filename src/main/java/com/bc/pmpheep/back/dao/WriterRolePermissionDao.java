package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.WriterRolePermission;

/**
 * WriterRolePermission 数据层实现接口
 * 
 * @author 曾庆峰 <791038935@qq.com>
 *
 */
@Repository
public interface WriterRolePermissionDao {

	/**
	 * 新增一个WriterRolePermission
	 * 
	 * @param WriterRolePermission
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addWriterRolePermission(WriterRolePermission writerRolePermission);

	/**
	 * 删除WriterRolePermission 通过主键id
	 * 
	 * @param WriterRolePermission
	 * @return 影响行数
	 */
	Integer deleteWriterRolePermissionById(Long id);

	/**
	 * 更新一个 WriterRolePermission通过主键id
	 * 
	 * @param WriterRolePermission
	 * @return 影响行数
	 */
	Integer updateWriterRolePermission(WriterRolePermission writerRolePermission);

	/**
	 * 查询一个 WriterRolePermission 通过主键id
	 * 
	 * @param WriterRolePermission
	 *            必须包含主键ID
	 * @return WriterRolePermission
	 */
	WriterRolePermission getWriterRolePermissionById(Long id);
	
    /**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的数据总条数
     * </pre>
     */
    Long getWriterRolePermissionCount();

}
