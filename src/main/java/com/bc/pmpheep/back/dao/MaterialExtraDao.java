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
	 * @param materialExtra
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialExtra(MaterialExtra materialExtra);

	/**
	 * 删除MaterialExtra 通过主键id
	 * 
	 * @param id 
	 * @return 影响行数
	 */
	Integer deleteMaterialExtraById(Long id);

	/**
	 * 根据主键id 更新materialExtra 不为null和不为‘’的字段
	 * @param materialExtra
	 * @return 影响行数
	 */
	Integer updateMaterialExtra(MaterialExtra materialExtra);

	/**
	 * 查询一个 MaterialExtra 通过主键id
	 * 
	 * @param id
	 *            必须包含主键ID
	 * @return MaterialExtra
	 */
	MaterialExtra getMaterialExtraById(Long id);
	
	/**
	 * 查询一个 MaterialExtra 通过materialId
	 * 
	 * @param id
	 *            必须包含主键ID
	 * @return MaterialExtra
	 */
	MaterialExtra getMaterialMaterialId(Long materialId);
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
	Long getMartialExtraCount();

}
