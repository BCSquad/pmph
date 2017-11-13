package com.bc.pmpheep.back.dao;

import java.util.List;

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
	 * 根据 materialId 获取 MaterialExtension结果集 
	 * @author Mryang
	 * @createDate 2017年11月13日 下午5:12:51
	 * @param materialId
	 * @return
	 */
	List<MaterialExtension> getMaterialExtensionByMaterialId(Long materialId);

	/**
	 * 新增一个MaterialExtension
	 * 
	 * @param materialExtension
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialExtension(MaterialExtension materialExtension);

	/**
	 * 删除MaterialExtension 通过主键id
	 * 
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteMaterialExtensionById(Long id);

	/**
	 * 通过主键id更新materialExtension不为null的字段
	 * 
	 * @param materialExtension
	 * @return 影响行数
	 */
	Integer updateMaterialExtension(MaterialExtension materialExtension);

	/**
	 * 查询一个 MaterialExtension 通过主键id
	 * 
	 * @param id
	 *            必须包含主键ID
	 * @return MaterialExtension
	 */
	MaterialExtension getMaterialExtensionById(Long id);
	
	/**
     * 
     * <pre>
     * 功能描述：查询表单的数据总条数
     * 使用示范：
     *
     * @return 表单的总条数
     * </pre>
     */
	Long getMaterialExtensionCount();

}
