package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialProjectEditor;

/**
 *@author MrYang 
 *@CreateDate 2017年11月1日 下午5:42:21
 *
 **/
@Repository
public interface MaterialProjectEditorDao {
	/**
	 * 新增一个 materialProjectEditor 
	 * @author Mryang
	 * @createDate 2017年11月1日 下午5:42:45
	 * @param materialProjectEditor
	 * @return 影响行数
	 */
	Integer addMaterialProjectEditor(MaterialProjectEditor materialProjectEditor) ;
	
	/**
	 * 批量增加 materialProjectEditors 
	 * @author Mryang
	 * @createDate 2017年11月1日 下午5:42:42
	 * @param materialProjectEditors
	 * @return 影响行数
	 */
	Integer addMaterialProjectEditors(List<MaterialProjectEditor> materialProjectEditors) ;
	
	/**
	 * 根据教材id删除 MaterialProjectEditor 
	 * @author Mryang
	 * @createDate 2017年11月1日 下午5:42:50
	 * @param materialId
	 * @return 影响行数
	 */
	Integer deleteMaterialProjectEditorByMaterialId(Long materialId) ;
	
	/**
	 * 根据教材id批量项目编辑
	 * @author Mryang
	 * @createDate 2017年11月17日 下午3:37:18
	 * @param materialId
	 * @return List<MaterialProjectEditor>
	 */
	List<MaterialProjectEditor> listMaterialProjectEditors(Long materialId);
	
}
