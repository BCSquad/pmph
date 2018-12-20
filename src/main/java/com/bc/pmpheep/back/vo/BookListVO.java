/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.Textbook;

/**
 * <p>Title:书目录视图层<p>
 * <p>Description:设置书目录<p>
 * @author lyc
 * @date 2017年11月22日 上午11:05:19
 */

@SuppressWarnings("serial")
@Alias("BookListVO")
public class BookListVO implements Serializable{

	//教材id
	private Long materialId ;
	//教材名称
	private String materialName;
	//教材轮次
	private Integer materialRound;
	//教材分类
	private String[] materialType;
	//可见性区别
	private Boolean isPublic;
    //教材书籍
	private Textbook textbook;
	//书籍序号
	private Integer bookSort;
	//是否允许被删除标识
	private Boolean allowedDelete;

	//调研表数量
	private Integer surveyNum;
	
	public BookListVO() {
		super();
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getMaterialRound() {
		return materialRound;
	}

	public void setMaterialRound(Integer materialRound) {
		this.materialRound = materialRound;
	}

	public String[] getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String[] materialType) {
		this.materialType = materialType;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Textbook getTextbook() {
		return textbook;
	}

	public void setTextbook(Textbook textbook) {
		this.textbook = textbook;
	}

	public Integer getBookSort() {
		return bookSort;
	}

	public void setBookSort(Integer bookSort) {
		this.bookSort = bookSort;
	}

	public Boolean getAllowedDelete() {
		return allowedDelete;
	}

	public void setAllowedDelete(Boolean allowedDelete) {
		this.allowedDelete = allowedDelete;
	}

	public Boolean getPublic() {
		return isPublic;
	}

	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
	}

	public Integer getSurveyNum() {
		return surveyNum;
	}

	public void setSurveyNum(Integer surveyNum) {
		this.surveyNum = surveyNum;
	}
}