/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:书目录视图层<p>
 * <p>Description:设置书目录<p>
 * @author lyc
 * @date 2017年11月22日 上午11:05:19
 */

@SuppressWarnings("serial")
@Alias("BookListVO")
public class BookListVO implements Serializable{

	//书籍id
	private Long id;
	//教材id
	private Long materialId ;
	//教材名称
	private String materialName;
	//教材轮次
	private Integer materialRound;
	//教材分类
	private String materialType;
	//书序
	private Integer sort;
	//书名
	private String textbookName;
	//版次
	private Integer textbookRound;
	
	public BookListVO() {
		super();
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
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTextbookName() {
		return textbookName;
	}
	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}
	public Integer getTextbookRound() {
		return textbookRound;
	}
	public void setTextbookRound(Integer textbookRound) {
		this.textbookRound = textbookRound;
	}
		
}
