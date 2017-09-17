package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialTypeService 接口
 * 
 * @author 曾庆峰
 *
 */
public interface MaterialTypeService {

	/**
	 * 新增一个MaterialType
	 * 
	 * @param MaterialType
	 *            实体对象
	 * @return 带主键的 MaterialType thorws CheckedServiceException
	 */
	MaterialType addMaterialType(MaterialType materialType) throws CheckedServiceException;

	/**
	 * 查询一个 MaterialType 通过主键id
	 * 
	 * @param id
	 * @return MaterialType
	 * @throws CheckedServiceException
	 */
	MaterialType getMaterialTypeById(Long id) throws CheckedServiceException;

	/**
	 * 删除MaterialType 通过主键id
	 * 
	 * @param MaterialType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialTypeById(Long id) throws CheckedServiceException;

	/**
	 * 更新一个 MaterialType通过主键id
	 * 
	 * @param MaterialType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateMaterialType(MaterialType materialType) throws CheckedServiceException;
}
