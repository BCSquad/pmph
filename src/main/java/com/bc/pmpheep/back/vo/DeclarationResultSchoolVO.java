/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:DeclarationResultSchoolVO<p>
 * <p>Description:申报结果按学校统计界面<p>
 * @author lyc
 * @date 2017年11月29日 下午2:10:18
 */

@SuppressWarnings("serial")
@Alias("DeclarationResultSchoolVO")
public class DeclarationResultSchoolVO implements Serializable{

	//教材id
	private Long materialId;
	//序号
	private Long row;
	//机构id
	private Long orgId;
	//机构名称
	private String schoolName;
	//主编名单
	private String editorList;
	//副主编名单
	private String subEditorList;
	//编委名单
	private String editorialList;
	//数字编委名单
	private String isDigitalEditorList;
	
	public DeclarationResultSchoolVO() {
		super();
	}
	
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getEditorList() {
		return editorList;
	}
	public void setEditorList(String editorList) {
		this.editorList = editorList;
	}
	public String getSubEditorList() {
		return subEditorList;
	}
	public void setSubEditorList(String subEditorList) {
		this.subEditorList = subEditorList;
	}
	public String getEditorialList() {
		return editorialList;
	}
	public void setEditorialList(String editorialList) {
		this.editorialList = editorialList;
	}
	public String getIsDigitalEditorList() {
		return isDigitalEditorList;
	}
	public void setIsDigitalEditorList(String isDigitalEditorList) {
		this.isDigitalEditorList = isDigitalEditorList;
	}
	
}
