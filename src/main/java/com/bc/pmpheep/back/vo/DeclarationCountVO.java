/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:DeclarationCountVO<p>
 * <p>Description:申请报情况总数统计<p>
 * @author lyc
 * @date 2017年11月30日 下午4:08:07
 */

@SuppressWarnings("serial")
@Alias("DeclarationCountVO")
public class DeclarationCountVO implements Serializable{

	//教材id
	private Long materialId;
	//院校申报总数
	private Integer schoolDeclarationCount;
	//院校当选总数
	private Integer schoolDeclarationChosenCount;
	//院校申报平均数
	private Integer schoolDeclarationAverage;
	//院校当选平均数
	private Integer schoolDeclarationChosenAverage;
	//主编申报总数
	private Integer editorCount;
	//副主编申报总数
	private Integer subEditorCount;
	//编委申报总数
	private Integer editorialCount;
	//数字编委申报数
	private Integer digitalCount;
	//主编当选总数
	private Integer chosenEditorCount;
	//副主编当选总数
	private Integer chosenSubeditorCount;
	//编委当选总数
	private Integer chosenEditorialCount;
	//数字编委当选总数
	private Integer chosenDigitalCount;
	
	public DeclarationCountVO() {
		super();
	}
	
	public Long getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
	public Integer getSchoolDeclarationCount() {
		return schoolDeclarationCount;
	}
	
	public void setSchoolDeclarationCount(Integer schoolDeclarationCount) {
		this.schoolDeclarationCount = schoolDeclarationCount;
	}
	
	public Integer getSchoolDeclarationChosenCount() {
		return schoolDeclarationChosenCount;
	}

	public void setSchoolDeclarationChosenCount(Integer schoolDeclarationChosenCount) {
		this.schoolDeclarationChosenCount = schoolDeclarationChosenCount;
	}

	public Integer getSchoolDeclarationAverage() {
		return schoolDeclarationAverage;
	}
	
	public void setSchoolDeclarationAverage(Integer schoolDeclarationAverage) {
		this.schoolDeclarationAverage = schoolDeclarationAverage;
	}
	
	public Integer getSchoolDeclarationChosenAverage() {
		return schoolDeclarationChosenAverage;
	}

	public void setSchoolDeclarationChosenAverage(
			Integer schoolDeclarationChosenAverage) {
		this.schoolDeclarationChosenAverage = schoolDeclarationChosenAverage;
	}

	public Integer getEditorCount() {
		return editorCount;
	}
	
	public void setEditorCount(Integer editorCount) {
		this.editorCount = editorCount;
	}
	
	public Integer getSubEditorCount() {
		return subEditorCount;
	}
	
	public void setSubEditorCount(Integer subEditorCount) {
		this.subEditorCount = subEditorCount;
	}
	
	public Integer getEditorialCount() {
		return editorialCount;
	}
	
	public void setEditorialCount(Integer editorialCount) {
		this.editorialCount = editorialCount;
	}

	public Integer getDigitalCount() {
		return digitalCount;
	}

	public void setDigitalCount(Integer digitalCount) {
		this.digitalCount = digitalCount;
	}

	public Integer getChosenEditorCount() {
		return chosenEditorCount;
	}

	public void setChosenEditorCount(Integer chosenEditorCount) {
		this.chosenEditorCount = chosenEditorCount;
	}

	public Integer getChosenSubeditorCount() {
		return chosenSubeditorCount;
	}

	public void setChosenSubeditorCount(Integer chosenSubeditorCount) {
		this.chosenSubeditorCount = chosenSubeditorCount;
	}

	public Integer getChosenEditorialCount() {
		return chosenEditorialCount;
	}

	public void setChosenEditorialCount(Integer chosenEditorialCount) {
		this.chosenEditorialCount = chosenEditorialCount;
	}

	public Integer getChosenDigitalCount() {
		return chosenDigitalCount;
	}

	public void setChosenDigitalCount(Integer chosenDigitalCount) {
		this.chosenDigitalCount = chosenDigitalCount;
	}
	
}
