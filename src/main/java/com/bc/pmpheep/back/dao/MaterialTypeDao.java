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
	 * 新增一个materialType
	 * 
	 * @param MaterialType
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialType(MaterialType materialType);

	/**
	 * 删除MaterialType 通过主键id
	 * 
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteMaterialTypeById(Long id);

	/**
	 * 根据主键id更新materialType 不为null和不为‘’的字段
	 * 
	 * @param materialType
	 * @return 影响行数
	 */
	Integer updateMaterialType(MaterialType materialType);

	/**
	 * 查询一个 MaterialType 通过主键id
	 * 
	 * @param id
	 *            必须包含主键ID
	 * @return MaterialType
	 */
	MaterialType getMaterialTypeById(Long id);
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
	Long getMaterialTypeCount();

}
