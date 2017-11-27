package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：教材通知备注VO
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-22
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("serial")
@Alias("MaterialExtraVO")
public class MaterialExtraVO implements Serializable {
    // 主键
    private Long     id;
    // 教材id
    private Long     materialId;
    // 通知内容
    private String   notice;
    // 备注
    private String   note;
    // 教材名称
    private String   materialName;
    // 教材通知附件
    private String[] noticeFiles;
    // 教材通知附件MongoDB对应ID
    private String[] noticeAttachments;
    // 教材备注附件
    private String[] noteFiles;
    // 教材备注附件MongoDB对应ID
    private String[] noteAttachments;
    // 完整内容
    private String   content;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the materialId
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * @return the notice
     */
    public String getNotice() {
        return notice;
    }

    /**
     * @param notice the notice to set
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the materialName
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * @param materialName the materialName to set
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * @return the noticeFiles
     */
    public String[] getNoticeFiles() {
        return noticeFiles;
    }

    /**
     * @param noticeFiles the noticeFiles to set
     */
    public void setNoticeFiles(String[] noticeFiles) {
        this.noticeFiles = noticeFiles;
    }

    /**
     * @return the noticeAttachments
     */
    public String[] getNoticeAttachments() {
        return noticeAttachments;
    }

    /**
     * @param noticeAttachments the noticeAttachments to set
     */
    public void setNoticeAttachments(String[] noticeAttachments) {
        this.noticeAttachments = noticeAttachments;
    }

    /**
     * @return the noteFiles
     */
    public String[] getNoteFiles() {
        return noteFiles;
    }

    /**
     * @param noteFiles the noteFiles to set
     */
    public void setNoteFiles(String[] noteFiles) {
        this.noteFiles = noteFiles;
    }

    /**
     * @return the noteAttachments
     */
    public String[] getNoteAttachments() {
        return noteAttachments;
    }

    /**
     * @param noteAttachments the noteAttachments to set
     */
    public void setNoteAttachments(String[] noteAttachments) {
        this.noteAttachments = noteAttachments;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}
