package com.bc.pmpheep.back.service;

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

}
