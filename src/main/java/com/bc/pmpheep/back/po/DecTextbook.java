package com.bc.pmpheep.back.po;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>
 * Title:作家教材编写情况表 实体类
 * <p>
 * <p>
 * Description:作家教材编写情况信息
 * <p>
 * 
 * @author lyc
 * @date 2017年9月22日 上午10:26:58
 */
@SuppressWarnings("serial")
@Alias("DecTextbook")
public class DecTextbook implements java.io.Serializable {

	// 主键
	private Long id;
	// 申报表id
	private Long declarationId;
	// 教材名称
	private String materialName;
	// 教材级别
	private Integer rank;
	// 是否为数字编辑
	private Boolean isDigitalEditor;
	// 编写职务
	private Integer position;
	// 出版社
	private String publisher;
	// 初版日期
	private Date publishDate;
	// 标准书号
	private String isbn;
	// 备注
	private String note;
	// 显示顺序
	private Integer sort;
	//个人资料id
	private String perId;
	//临床决策申报id
	private String expertationId;

	// 构造器

	/** default constructor */
	public DecTextbook() {
	}

	public DecTextbook(Long id) {
		super();
		this.id = id;
	}

	public DecTextbook(Long declarationId, String materialName, Integer rank, Boolean isDigitalEditor, Integer position,
			String publisher, Date publishDate, String isbn, String note, Integer sort) {
		super();
		this.declarationId = declarationId;
		this.materialName = materialName;
		this.rank = rank;
		this.isDigitalEditor = isDigitalEditor;
		this.position = position;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.isbn = isbn;
		this.note = note;
		this.sort = sort;
	}
	
	public DecTextbook(Long declarationId, String materialName, Integer rank, Integer position,
			String publisher, Date publishDate, String isbn, String note, Integer sort) {
		super();
		this.declarationId = declarationId;
		this.materialName = materialName;
		this.rank = rank;
		this.position = position;
		this.publisher = publisher;
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

	public Boolean getIsDigitalEditor() {
		return isDigitalEditor;
	}

	public void setIsDigitalEditor(Boolean isDigitalEditor) {
		this.isDigitalEditor = isDigitalEditor;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
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

	@Override
	public String toString() {
		return "DecTextbook [id=" + id + ", declarationId=" + declarationId + ", materialName=" + materialName
				+ ", rank=" + rank + ", isDigitalEditor=" + isDigitalEditor + ", position=" + position + ", publisher="
				+ publisher + ", publishDate=" + publishDate + ", isbn=" + isbn + ", note=" + note + ", sort=" + sort
				+ "]";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public Boolean getDigitalEditor() {
		return isDigitalEditor;
	}

	public void setDigitalEditor(Boolean digitalEditor) {
		isDigitalEditor = digitalEditor;
	}

	public String getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(String expertationId) {
		this.expertationId = expertationId;
	}
}