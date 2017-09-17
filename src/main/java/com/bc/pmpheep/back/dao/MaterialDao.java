package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.Material;

/**
 * MaterialDao实体类数据访问层接口
 * @author 曾庆峰
 *
 */
@Repository
public interface MaterialDao {

	/**
	 * 新增一个Material
	 * 
	 * @param Material
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterial(Material material);

	/**
	 * 删除Material 通过主键id
	 * 
	 * @param Material
	 * @return 影响行数
	 */
	Integer deleteMaterialById(Long id);

	/**
	 * 更新一个 Material通过主键id
	 * 
	 * @param Material
	 * @return 影响行数
	 */
	Integer updateMaterial(Material material);

	/**
	 * 查询一个 Material 通过主键id
	 * 
	 * @param Material
	 *            必须包含主键ID
	 * @return Material
	 */
	Material getMaterialById(Long id);
}
