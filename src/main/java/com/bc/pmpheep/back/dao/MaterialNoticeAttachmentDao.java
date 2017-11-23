/**
 * 
 */
package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.MaterialNoticeAttachment;

/**
 * 教材通知附件表
 * 
 * @introduction
 * 
 * @author Mryang
 * 
 * @createDate 2017年10月25日 下午3:33:18
 * 
 */
@Repository
public interface MaterialNoticeAttachmentDao {

    /**
     * 新增 materialNoticeAttachment
     * 
     * @introduction
     * @author Mryang
     * @createDate 2017年10月25日 下午3:34:22
     * @param materialNoticeAttachment
     * @return 影响行数
     */
    Integer addMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment);

    /**
     * 更新 materialNoticeAttachment
     * 
     * @introduction
     * @author Mryang
     * @createDate 2017年10月25日 下午3:44:07
     * @param materialNoticeAttachment
     * @return 影响行数
     */
    Integer updateMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment);

    /**
     * 根据教材通知备注id获取 MaterialNoticeAttachment
     * 
     * @author Mryang
     * @createDate 2017年11月14日 上午9:43:58
     * @param materialExtraId
     * @return List<MaterialNoticeAttachment>
     */
    List<MaterialNoticeAttachment> getMaterialNoticeAttachmentsByMaterialExtraId(
    Long materialExtraId);

    /**
     * 
     * <pre>
     * 功能描述：按attachment修改下载次数 
     * 使用示范：
     *
     * @param attachment  MongoDB附件表的主键
     * @return 影响行数
     * </pre>
     */
    Integer updateMaterialNoticeAttachmentDownLoadCountsByAttachment(String attachment);

    /**
     * 根据教材通知备注id删除 MaterialNoticeAttachment
     * 
     * @author Mryang
     * @createDate 2017年11月14日 上午9:44:34
     * @param materialExtraId
     * @return
     */
    Integer deleteMaterialNoticeAttachmentsByMaterialExtraId(Long materialExtraId);

    /**
     * 根据主键id删除 MaterialNoticeAttachment
     * 
     * @author Mryang
     * @createDate 2017年11月14日 上午9:44:34
     * @param id
     * @return
     */
    Integer deleteMaterialNoticeAttachmentById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：按教材通知附件MongoDB对应ID删除对应附件
     * 使用示范：
     *
     * @param noticeAttachments 教材通知附件IDs
     * @return 影响行数 
     * </pre>
     */
    Integer deleteMaterialNoticeAttachmentByAttachments(String[] noticeAttachments);
}
