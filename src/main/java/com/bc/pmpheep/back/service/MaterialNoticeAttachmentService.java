package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 *@author MrYang 
 *@CreateDate 2017年10月25日 下午3:25:58
 *
 **/
public interface MaterialNoticeAttachmentService {
	
	/**
	 * 新增 materialNoticeAttachment返回带主键的materialNoticeAttachment 
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:27:23
	 * @param materialNoticeAttachment
	 * @return
	 * @throws CheckedServiceException
	 */
	MaterialNoticeAttachment addMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment) throws CheckedServiceException;
	
	/**
	 * 更新 materialNoticeAttachment
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:44:07
	 * @param materialNoticeAttachment
	 * @return 影响行数
	 */
	Integer updateMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment)throws CheckedServiceException;
	
	/**
	 * 根据教材通知备注id获取 MaterialNoticeAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午9:43:58
	 * @param materialExtraId
	 * @return List<MaterialNoticeAttachment> 
	 */
	List<MaterialNoticeAttachment> getMaterialNoticeAttachmentsByMaterialExtraId(Long materialExtraId)throws CheckedServiceException;
	
	/**
	 * 根据教材通知备注id删除 MaterialNoticeAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午9:44:34
	 * @param materialExtraId
	 * @return
	 */
	Integer deleteMaterialNoticeAttachmentsByMaterialExtraId(Long materialExtraId)throws CheckedServiceException;
	
	/**
	 * 根据主键id删除 MaterialNoticeAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午9:44:34
	 * @param id
	 * @return
	 */
	Integer deleteMaterialNoticeAttachmentsById(Long id)throws CheckedServiceException;

}





