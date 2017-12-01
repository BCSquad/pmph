/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:DeclarationSituationSchoolResult<p>
 * <p>Description:申报情况按学校统计界面<p>
 * @author lyc
 * @date 2017年11月29日 上午9:19:40
 */
@SuppressWarnings("serial")
@Alias("DeclarationSituationSchoolResultVO")
public class DeclarationSituationSchoolResultVO implements Serializable{

	//教材id
	private Long materialId;
	//机构id
	private Long orgId;
	//序号
	private Long row;
	//申报学校
	private String schoolName;
	//主编申报数
	private Integer presetPositionEditor;
	//副主编申报数
	private Integer presetPositionSubeditor;
	//编委申报数
	private Integer presetPositionEditorial;
	//主编当选数
	private Integer chosenPositionEditor;
	//副主编当选数
	private Integer chosenPositionSubeditor;
	//编委当选数
	private Integer chosenPositionEditorial;
	//数字编委当选数
	private Integer isDigitalEditor;
	//申报人数
	private Integer presetPersons;
	//当选人数
	private Integer chosenPersons;
	
	public DeclarationSituationSchoolResultVO() {
		super();
	}
	
	public Long getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public Long getRow() {
		return row;
	}
	
	public void setRow(Long row) {
		this.row = row;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public Integer getPresetPositionEditor() {
		return presetPositionEditor;
	}
	
	public void setPresetPositionEditor(Integer presetPositionEditor) {
		this.presetPositionEditor = presetPositionEditor;
	}
	
	public Integer getPresetPositionSubeditor() {
		return presetPositionSubeditor;
	}
	
	public void setPresetPositionSubeditor(Integer presetPositionSubeditor) {
		this.presetPositionSubeditor = presetPositionSubeditor;
	}
	
	public Integer getPresetPositionEditorial() {
		return presetPositionEditorial;
	}
	
	public void setPresetPositionEditorial(Integer presetPositionEditorial) {
		this.presetPositionEditorial = presetPositionEditorial;
	}
	
	public Integer getChosenPositionEditor() {
		return chosenPositionEditor;
	}
	
	public void setChosenPositionEditor(Integer chosenPositionEditor) {
		this.chosenPositionEditor = chosenPositionEditor;
	}
	
	public Integer getChosenPositionSubeditor() {
		return chosenPositionSubeditor;
	}
	
	public void setChosenPositionSubeditor(Integer chosenPositionSubeditor) {
		this.chosenPositionSubeditor = chosenPositionSubeditor;
	}
	
	public Integer getChosenPositionEditorial() {
		return chosenPositionEditorial;
	}
	
	public void setChosenPositionEditorial(Integer chosenPositionEditorial) {
		this.chosenPositionEditorial = chosenPositionEditorial;
	}
	
	public Integer getIsDigitalEditor() {
		return isDigitalEditor;
	}

	public void setIsDigitalEditor(Integer isDigitalEditor) {
		this.isDigitalEditor = isDigitalEditor;
	}

	public Integer getPresetPersons() {
		return presetPersons;
	}

	public void setPresetPersons(Integer presetPersons) {
		this.presetPersons = presetPersons;
	}

	public Integer getChosenPersons() {
		return chosenPersons;
	}
	
	public void setChosenPersons(Integer chosenPersons) {
		this.chosenPersons = chosenPersons;
	}

}
