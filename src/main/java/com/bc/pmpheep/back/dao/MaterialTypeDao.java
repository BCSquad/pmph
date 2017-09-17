package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialType;

/**
 * MaterialTypeDao实体类数据访问层接口
 * @author 曾庆峰
 *
 */
@Repository
public interface MaterialTypeDao {

	/**
	 * 新增一个MaterialType
	 * 
	 * @param MaterialType
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialType(MaterialType materialType);

	/**
	 * 删除MaterialType 通过主键id
	 * 
	 * @param MaterialType
	 * @return 影响行数
	 */
	Integer deleteMaterialTypeById(Long id);

	/**
	 * 更新一个 MaterialType通过主键id
	 * 
	 * @param MaterialType
	 * @return 影响行数
	 */
	Integer updateMaterialType(MaterialType materialType);

	/**
	 * 查询一个 MaterialType 通过主键id
	 * 
	 * @param MaterialType
	 *            必须包含主键ID
	 * @return MaterialType
	 */
	MaterialType getMaterialTypeById(Long id);

}
