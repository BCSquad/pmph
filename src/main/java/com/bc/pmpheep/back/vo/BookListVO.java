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

	//教材id
	private Long materialId ;
	//教材名称
	private String materialName;
	//教材轮次
	private Integer materialRound;
	//教材分类
	private String[] materialType;
	//可见性区别
	private boolean isPublic;
    //教材书籍
	private String textbooks;
	
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getTextbooks() {
		return textbooks;
	}

	public void setTextbooks(String textbooks) {
		this.textbooks = textbooks;
	}
}