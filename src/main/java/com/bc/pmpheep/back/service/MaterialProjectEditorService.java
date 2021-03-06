package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年11月1日 下午5:32:56
 *
 **/
public interface MaterialProjectEditorService {
	/**
	 * 新增materialProjectEditor
	 * 
	 * @author Mryang
	 * @createDate 2017年11月1日 下午3:25:33
	 * @param materialOrg
	 * @return 带主键的materialProjectEditor
	 * @throws CheckedServiceException
	 */
	MaterialProjectEditor addMaterialProjectEditor(MaterialProjectEditor materialProjectEditor)
			throws CheckedServiceException;

	/**
	 * 批量新增 materialProjectEditor
	 * 
	 * @author Mryang
	 * @createDate 2017年11月1日 下午3:25:33
	 * @param materialProjectEditors
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer addMaterialProjectEditors(List<MaterialProjectEditor> materialProjectEditors)
			throws CheckedServiceException;

	/**
	 * 根据教材id批量查找项目编辑
	 * 
	 * @author Mryang
	 * @createDate 2017年11月17日 下午3:37:18
	 * @param materialId
	 * @return List<MaterialProjectEditor>
	 * @throws CheckedServiceException
	 */
	List<MaterialProjectEditorVO> listMaterialProjectEditors(Long materialId) throws CheckedServiceException;

	/**
	 * 根据materialId删除materialProjectEditor
	 * 
	 * @author Mryang
	 * @createDate 2017年11月1日 下午3:49:52
	 * @param materialId
	 * @return 影响行数
	 */
	Integer deleteMaterialProjectEditorByMaterialId(Long materialId) throws CheckedServiceException;

	/**
	 * 
	 * 
	 * 功能描述：查询该用户在该教材中是否是项目编辑
	 * 
	 * @param materialId
	 *            教材id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws CheckedServiceException
	 *
	 */
	MaterialProjectEditor getMaterialProjectEditorByMaterialIdAndUserId(Long materialId, Long userId)
			throws CheckedServiceException;
}
