/**
 * 
 */
package com.bc.pmpheep.back.dao;


import java.util.List;
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
	
	/**
	 * 
	 * 通过主键id删除 MaterialNoteAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午10:05:27
	 * @param id
	 * @return 影响行数
	 */
	Integer deleteMaterialNoteAttachmentById(Long id);
	
	/**
	 * 
	 * 通过materialExtraId删除 MaterialNoteAttachment
	 * @author Mryang
	 * @createDate 2017年11月14日 上午10:05:55
	 * @param materialExtraId
	 * @return  影响行数
	 */
	Integer deleteMaterialNoteAttachmentByMaterialExtraId(Long materialExtraId);
	
	/**
	 * 
	 * 根据 materialExtraId获取 MaterialNoteAttachment 
	 * @author Mryang
	 * @createDate 2017年11月14日 上午10:06:09
	 * @param materialExtraId
	 * @return List<MaterialNoteAttachment> 
	 */
	List<MaterialNoteAttachment> getMaterialNoteAttachmentByMaterialExtraId(Long materialExtraId);
}
