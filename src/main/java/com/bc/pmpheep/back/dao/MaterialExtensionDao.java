package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialExtension;

/**
 * MaterialExtensionDao实体类数据访问层接口
 * @author 曾庆峰
 *
 */
@Repository
public interface MaterialExtensionDao {

	/**
	 * 新增一个MaterialExtension
	 * 
	 * @param MaterialExtension
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialExtension(MaterialExtension materialExtension);

	/**
	 * 删除MaterialExtension 通过主键id
	 * 
	 * @param MaterialExtension
	 * @return 影响行数
	 */
	Integer deleteMaterialExtensionById(Long id);

	/**
	 * 更新一个 MaterialExtension通过主键id
	 * 
	 * @param MaterialExtension
	 * @return 影响行数
	 */
	Integer updateMaterialExtension(MaterialExtension materialExtension);

	/**
	 * 查询一个 MaterialExtension 通过主键id
	 * 
	 * @param MaterialExtension
	 *            必须包含主键ID
	 * @return MaterialExtension
	 */
	MaterialExtension getMaterialExtensionById(Long id);

}
