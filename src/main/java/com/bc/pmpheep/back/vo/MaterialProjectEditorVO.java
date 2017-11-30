package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年11月29日 下午2:08:55
 *
 **/
@SuppressWarnings("serial")
@Alias("MaterialProjectEditorVO")
public class MaterialProjectEditorVO implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 项目编辑名称
	 */
	private String realname ;
	/**
	 * 教材id
	 */
	private Long materialId;
	/**
	 * 项目编辑id
	 */
	private Long editorId;
	
	public MaterialProjectEditorVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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
		return "{id:" + id + ", realname:" + realname + ", materialId:"
				+ materialId + ", editorId:" + editorId + "}";
	}
	
	

}
