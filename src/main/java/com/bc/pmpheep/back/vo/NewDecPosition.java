package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *@author MrYang 
 *@CreateDate 2017年11月27日 下午2:42:41
 *
 **/

@SuppressWarnings("serial")
@Alias("NewDecPosition")
public class NewDecPosition implements Serializable {
	private Long id;
	private Long declarationId;
	private Long textbookId;
	private Integer presetPosition;
	private String file;
	// 是否为数字编辑
    @JsonProperty("isDigitalEditor")
    private Boolean   isDigitalEditor;
	public Boolean getIsDigitalEditor() {
		return isDigitalEditor;
	}
	public void setIsDigitalEditor(Boolean isDigitalEditor) {
		this.isDigitalEditor = isDigitalEditor;
	}
	public NewDecPosition() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeclarationId() {
		return declarationId;
	}
	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}
	public Long getTextbookId() {
		return textbookId;
	}
	public void setTextbookId(Long textbookId) {
		this.textbookId = textbookId;
	}
	public Integer getPresetPosition() {
		return presetPosition;
	}
	public void setPresetPosition(Integer presetPosition) {
		this.presetPosition = presetPosition;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}
