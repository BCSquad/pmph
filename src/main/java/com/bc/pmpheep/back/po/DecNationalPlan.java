package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家主编国家级规划教材情况表 实体类<p>
 * <p>Description:作家主编国家级教材规划情况信息<p>
 * @author lyc
 * @date 2017年9月22日 上午9:57:49
 */
@SuppressWarnings("serial")
@Alias("DecNationalPlan")
public class DecNationalPlan implements java.io.Serializable {

	//主键
	private Long id;
	//申报表id
	private Long declarationId;
	//教材名称
	private String materialName;
	//标准书号
	private String isbn;
	//教材级别
	private Integer rank;
	// 教材级别(文字输入)
	private String rankText;
	//备注
	private String note;
	//显示顺序
	private Integer sort;
	//个人资料id
	private String perId;
	//临床决策申报id
	private String expertationId;

	// 构造器

	/** default constructor */
	public DecNationalPlan() {
	}

	public DecNationalPlan(Long id) {
		super();
		this.id = id;
	}

	/** full constructor */
	public DecNationalPlan(Long declarationId, String materialName,
			String isbn, Integer rank, String rankText, String note, Integer sort) {
		this.declarationId = declarationId;
		this.materialName = materialName;
		this.isbn = isbn;
		this.rank = rank;
		this.rankText = rankText;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
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

	public String getRankText() {
		return rankText;
	}

	public void setRankText(String rankText) {
		this.rankText = rankText;
	}

	@Override
	public String toString() {
		return "DecNationalPlan [id=" + id + ", declarationId=" + declarationId
				+ ", materialName=" + materialName + ", isbn=" + isbn
				+ ", rank=" + rank + ", rankText=" + rankText + ", note="
				+ note + ", sort=" + sort + "]";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(String expertationId) {
		this.expertationId = expertationId;
	}
}