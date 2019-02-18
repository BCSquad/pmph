package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * 主编学术专著情况表实体类
 * 
 * @author tyc 2018年1月16日09:15
 */
@SuppressWarnings("serial")
@Alias("DecMonograph")
public class DecMonograph implements Serializable {
	// 主键
	private Long id;
	// 申报表id
	private Long declarationId;
	// 专著名称
	private String monographName;
	// 专著发表日期
	private Date monographDate;
	// 是否自费（0=公费/1=自费）
	private Boolean isSelfPaid;
	// 出版单位
	private String publisher;
	// 出版时间
	private Date publishDate;
	// 备注
	private String note;
	// 显示顺序
	private Integer sort;
	//个人资料id
	private String perId;
	//临床决策申报id
	private Long expertationId;

	public DecMonograph() {

	}

	public DecMonograph(Long declarationId, String monographName, Date monographDate, Boolean isSelfPaid,
			String publisher, Date publishDate, String note, Integer sort) {
		super();
		this.declarationId = declarationId;
		this.monographName = monographName;
		this.monographDate = monographDate;
		this.isSelfPaid = isSelfPaid;
		this.publisher = publisher;
		this.publishDate = publishDate;
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

	public String getMonographName() {
		return monographName;
	}

	public void setMonographName(String monographName) {
		this.monographName = monographName;
	}

	public Date getMonographDate() {
		return monographDate;
	}

	public void setMonographDate(Date monographDate) {
		this.monographDate = monographDate;
	}

	public Boolean getIsSelfPaid() {
		return isSelfPaid;
	}

	public void setIsSelfPaid(Boolean isSelfPaid) {
		this.isSelfPaid = isSelfPaid;
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
		return "DecMonograph [id=" + id + ", declarationId=" + declarationId + ", monographName=" + monographName
				+ ", monographDate=" + monographDate + ", isSelfPaid=" + isSelfPaid + ", publisher=" + publisher
				+ ", publishDate=" + publishDate + ", note=" + note + ", sort=" + sort + "]";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public Boolean getSelfPaid() {
		return isSelfPaid;
	}

	public void setSelfPaid(Boolean selfPaid) {
		isSelfPaid = selfPaid;
	}

	public Long getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(Long expertationId) {
		this.expertationId = expertationId;
	}
}
