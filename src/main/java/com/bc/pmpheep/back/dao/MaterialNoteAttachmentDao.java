/**
 * 
 */
package com.bc.pmpheep.back.dao;


import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;


/**
 * 教材备注附件表
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月25日 下午3:33:18
 *
 */
@Repository
public interface MaterialNoteAttachmentDao {
	
	/**
	 * 新增 materialNoteAttachment
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:34:22
	 * @param materialNoteAttachment
	 * @return 影响行数
	 */
	Integer addMaterialNoteAttachment(MaterialNoteAttachment materialNoteAttachment);
	
	/**
	 * 更新 materialNoteAttachment
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:44:07
	 * @param materialNoteAttachment
	 * @return 影响行数
	 */
	Integer updateMaterialNoteAttachment(MaterialNoteAttachment materialNoteAttachment);
}
