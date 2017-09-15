package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * MaterialExtension 教材通知备注附件表    实体类 
 * @author mryang 
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialExtra")
public class MaterialExtra implements java.io.Serializable {

	// Fields
	//主键
	private Long id;
	//教材id
	private Long materialId;
	//通知内容
	private String notice;
	//通知内容附件
	private String noticeAttachment;
	//备注
	private String note;
	//备注附件
	private String noteAttachment;

	/** default constructor */
	public MaterialExtra() {
	}

	/** full constructor */
	public MaterialExtra(Long materialId, String notice,
			String noticeAttachment, String note, String noteAttachment) {
		this.materialId = materialId;
		this.notice = notice;
		this.noticeAttachment = noticeAttachment;
		this.note = note;
		this.noteAttachment = noteAttachment;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getNoticeAttachment() {
		return this.noticeAttachment;
	}

	public void setNoticeAttachment(String noticeAttachment) {
		this.noticeAttachment = noticeAttachment;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteAttachment() {
		return this.noteAttachment;
	}

	public void setNoteAttachment(String noteAttachment) {
		this.noteAttachment = noteAttachment;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", notice:"
				+ notice + ", noticeAttachment:" + noticeAttachment + ", note:"
				+ note + ", noteAttachment:" + noteAttachment + "}";
	}
	
	
}