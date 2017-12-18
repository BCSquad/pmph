/**
 * 
 */
package com.bc.pmpheep.back.vo;

/**
 * <p>Title:DeclarationCountVO<p>
 * <p>Description:申请报情况总数统计<p>
 * @author lyc
 * @date 2017年11月30日 下午4:08:07
 */
public class DeclarationCountVO {

	//教材id
	private Long materialId;
	//院校申报总数
	private Integer schoolDeclarationCount;
	//院校申报平均数
	private Integer schoolDeclarationAverage;
	//主编申报总数
	private Integer editorCount;
	//副主编申报总数
	private Integer subEditorCount;
	//编委申报总数
	private Integer editorialCount;
	//数字编委申报数
	private Integer digitalCount;
	
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
	
	public Integer getSchoolDeclarationAverage() {
		return schoolDeclarationAverage;
	}
	
	public void setSchoolDeclarationAverage(Integer schoolDeclarationAverage) {
		this.schoolDeclarationAverage = schoolDeclarationAverage;
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
	
}
