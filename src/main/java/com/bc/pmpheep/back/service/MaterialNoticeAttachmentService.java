package com.bc.pmpheep.back.service;

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

}
