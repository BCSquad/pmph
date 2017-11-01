package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家上版教材参编情况表 实体类<p>
 * <p>Description:作家上版教材参编信息<p>
 * @author lyc
 * @date 2017年9月22日 上午9:55:09
 */
@SuppressWarnings("serial")
@Alias("DecLastPosition")
public class DecLastPosition implements java.io.Serializable {

	//主键
	private Long id;
	//申报表id
	private Long declarationId;
	//教材名称
	private String materialName;
	//编写职务
	private Integer position;
	//备注
	private String note;
	//显示顺序
	private Integer sort;

	// 构造器

	/** default constructor */
	public DecLastPosition() {
	}

	

	public DecLastPosition(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public DecLastPosition(Long declarationId, String materialName,
			Integer position, String note, Integer sort) {
		this.declarationId = declarationId;
		this.materialName = materialName;
		this.position = position;
		this.note = note;
		this.sort = sort;
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

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return " {id:" + id + ", declarationId:" + declarationId
				+ ", materialName:" + materialName + ", position:" + position
				+ ", note:" + note + ", sort:" + sort + "}";
	}
}