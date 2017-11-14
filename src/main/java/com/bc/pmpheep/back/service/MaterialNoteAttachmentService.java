package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年10月25日 下午3:25:58
 *
 **/
public interface MaterialNoteAttachmentService {
	
	/**
	 * 新增 materialNoteAttachment返回带主键的materialNoteAttachment 
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:27:23
	 * @param materialNoteAttachment
	 * @return
	 * @throws CheckedServiceException
	 */
	MaterialNoteAttachment addMaterialNoteAttachment(MaterialNoteAttachment materialNoteAttachment) throws CheckedServiceException;
	
	/**
	 * 更新 materialNoteAttachment
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:44:07
	 * @param materialNoteAttachment
	 * @return 影响行数
	 */
	Integer updateMaterialNoteAttachment(MaterialNoteAttachment materialNoteAttachment)throws CheckedServiceException;
	
	/**
	 * 
	 * 通过主键id删除 MaterialNoteAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午10:05:27
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialNoteAttachmentById(Long id)throws CheckedServiceException;
	
	/**
	 * 
	 * 通过materialExtraId删除 MaterialNoteAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午10:05:55
	 * @param materialExtraId
	 * @return  影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteMaterialNoteAttachmentByMaterialExtraId(Long materialExtraId)throws CheckedServiceException;
	
	/**
	 * 
	 * 根据 materialExtraId获取 MaterialNoteAttachment 
	 * @author Mryang
	 * @createDate 2017年11月14日 上午10:06:09
	 * @param materialExtraId
	 * @return List<MaterialNoteAttachment> 
	 * @throws CheckedServiceException
	 */
	List<MaterialNoteAttachment> getMaterialNoteAttachmentByMaterialExtraId(Long materialExtraId)throws CheckedServiceException;

}
