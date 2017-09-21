package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialContact;

/**
 * MaterialContactDao实体类数据访问层接口
 * @author 曾庆峰
 *
 */
@Repository
public interface MaterialContactDao {

	/**
	 * 新增一个MaterialContact
	 * 
	 * @param materialContact
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addMaterialContact(MaterialContact materialContact);

	/**
	 * 删除MaterialContact 通过主键id
	 * 
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteMaterialContactById(Long id);

	/**
	 *根据id 更新materialContact不为null和''的字段
	 * 
	 * @param materialContact
	 * @return 影响行数
	 */
	Integer updateMaterialContact(MaterialContact materialContact);

	/**
	 * 查询一个 MaterialContact 通过主键id
	 * 
	 * @param MaterialContact
	 *            必须包含主键ID
	 * @return MaterialContact
	 */
	MaterialContact getMaterialContactById(Long id);
	
	/**
	 * 
	 * <pre>
	 * 功能描述：查询表单的数据总条数
	 * 使用示范：
	 *
	 * @return 表单的总条数
	 * </pre>
	 */
	Long getMaterialContactCount();
}
