package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * SCI论文投稿及影响因子情况表实体类
 * @author tyc
 * 2018年1月16日10:27
 */
@SuppressWarnings("serial")
@Alias("DecSci")
public class DecSci implements Serializable {
	// 主键
    private Long id;
    // 申报表id
    private Long declarationId;
    // 论文名称
    private String paperName;
    // 期刊名称
    private String journalName;
    // 期刊SCI影响因子
    private String factor;
    // 发表时间
    private Date publishDate;
    // 备注
    private String note;
    // 显示顺序
    private Integer sort;
    
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
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
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
		return "DecSci [id=" + id + ", declarationId=" + declarationId
				+ ", paperName=" + paperName + ", journalName=" + journalName
				+ ", factor=" + factor + ", publishDate=" + publishDate
				+ ", note=" + note + ", sort=" + sort + "]";
	}
}
