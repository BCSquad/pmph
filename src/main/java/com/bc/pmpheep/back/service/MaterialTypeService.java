package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.vo.MaterialTypeVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * MaterialTypeService 接口
 * 
 * @author 曾庆峰
 *
 */
public interface MaterialTypeService {

	/**
	 * 新增一个materialType
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
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialTypeById(Long id) throws CheckedServiceException;

	/**
	 * 根据主键id更新materialType 不为null和不为‘’的字段
	 * 
	 * @param materialType
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateMaterialType(MaterialType materialType) throws CheckedServiceException;

	/**
	 * 
	 * 获取所有书籍类别(一次性加载层叠各下级类别 用于非懒加载)
	 * 
	 * @author 曾庆峰
	 * @param parentId
	 * @return 已经分级的书籍类别
	 * @throws CheckedServiceException
	 * @update Mryang
	 */
	MaterialTypeVO listMaterialType(Long parentId) throws CheckedServiceException;


	/**
	 * 获取下一级所有书籍类别(用于tree懒加载)
	 * @param parentId
	 * @return
	 */
	MaterialTypeVO lazyListMaterialType(Long parentId);
}
