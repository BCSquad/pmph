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
	 * 通过主键id更新material 不为null 的字段 
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
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
	Long getMaterialCount();
}
