package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 人卫社教材编写情况表实体类
 * @author tyc
 * 2018年02月28日下午16:57
 */
@SuppressWarnings("serial")
@Alias("DecTextbookPmph")
public class DecTextbookPmph implements Serializable {
	// 主键
    private Long id;
    // 申报表id
    private Long declarationId;
    // 教材名称
    private String materialName;
    // 教材级别
    private Integer rank;
    private String rankName;
    // 编写职务
    private Integer position;
    private String positionName;
    // 是否数字编委
    private Boolean isDigitalEditor;
    // 出版时间
    private Date publishDate;
    // 标准书号
 	private String isbn;
 	// 备注
 	private String note;
 	// 显示顺序
 	private Integer sort;
 	//个人资料id
 	private String perId;

 	private Long expertationId;
 	
 	// 构造器
 	public DecTextbookPmph(){
 		
 	}
 	
 	public DecTextbookPmph(Long declarationId, String materialName, Integer rank, Integer position, 
 			Boolean isDigitalEditor, Date publishDate, String isbn, String note, Integer sort){
 		super();
 		this.declarationId = declarationId;
 		this.materialName = materialName;
 		this.rank = rank;
 		this.position = position;
 		this.isDigitalEditor = isDigitalEditor;
 		this.publishDate = publishDate;
 		this.isbn = isbn;
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getIsDigitalEditor() {
		return isDigitalEditor;
	}

	public void setIsDigitalEditor(Boolean isDigitalEditor) {
		this.isDigitalEditor = isDigitalEditor;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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




	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Boolean getDigitalEditor() {
		return isDigitalEditor;
	}

	public void setDigitalEditor(Boolean digitalEditor) {
		isDigitalEditor = digitalEditor;
	}

	public Long getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(Long expertationId) {
		this.expertationId = expertationId;
	}

	@Override
	public String toString() {
		return "DecTextbookPmph{" +
				"id=" + id +
				", declarationId=" + declarationId +
				", materialName='" + materialName + '\'' +
				", rank=" + rank +
				", rankName='" + rankName + '\'' +
				", position=" + position +
				", positionName='" + positionName + '\'' +
				", isDigitalEditor=" + isDigitalEditor +
				", publishDate=" + publishDate +
				", isbn='" + isbn + '\'' +
				", note='" + note + '\'' +
				", sort=" + sort +
				", perId='" + perId + '\'' +
				", expertationId=" + expertationId +
				'}';
	}
}