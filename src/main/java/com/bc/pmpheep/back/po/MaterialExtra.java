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
	//备注
	private String note;

	/** default constructor */
	public MaterialExtra() {
	}
	

	public MaterialExtra(Long id) {
		super();
		this.id = id;
	}

	
	
	public MaterialExtra(Long materialId, String notice, String note) {
		super();
		this.materialId = materialId;
		this.notice = notice;
		this.note = note;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getMaterialId() {
		return materialId;
	}


	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}


	public String getNotice() {
		return notice;
	}


	public void setNotice(String notice) {
		this.notice = notice;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", notice:"
				+ notice + ", note:" + note + "}";
	}

	
	
}