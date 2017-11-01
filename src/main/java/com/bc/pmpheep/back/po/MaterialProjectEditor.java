package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 教材-项目编辑关联表（多对多）
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:31:36
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialProjectEditor")
public class MaterialProjectEditor implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 教材id
	 */
	private Long materialId;
	/**
	 * 项目编辑id
	 */
	private Long editorId;

	public MaterialProjectEditor() {
	
	}

	
	public MaterialProjectEditor(Long materialId, Long editorId) {
		super();
		this.materialId = materialId;
		this.editorId = editorId;
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

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", editorId:"
				+ editorId + "}";
	}

	

}