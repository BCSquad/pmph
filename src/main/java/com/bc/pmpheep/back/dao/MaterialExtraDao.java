package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialExtra;

/**
 * MaterialExtraDao实体类数据访问层接口
 * @author 曾庆峰
 *
 */
@Repository
public interface MaterialExtraDao {

	/**
	 * 新增一个MaterialExtra
	 * 
	 * @param MaterialExtra
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialExtra(MaterialExtra materialExtra);

	/**
	 * 删除MaterialExtra 通过主键id
	 * 
	 * @param MaterialExtra
	 * @return 影响行数
	 */
	Integer deleteMaterialExtraById(Long id);

	/**
	 * 更新一个 MaterialExtra通过主键id
	 * 
	 * @param MaterialExtra
	 * @return 影响行数
	 */
	Integer updateMaterialExtra(MaterialExtra materialExtra);

	/**
	 * 查询一个 MaterialExtra 通过主键id
	 * 
	 * @param MaterialExtra
	 *            必须包含主键ID
	 * @return MaterialExtra
	 */
	MaterialExtra getMaterialExtraById(Long id);

}
