package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家教学经历表 实体类<p>
 * <p>Description:作家教学经历信息<p>
 * @author lyc
 * @date 2017年9月22日 上午10:24:29
 */
@SuppressWarnings("serial")
@Alias("DecTeachExp")
public class DecTeachExp implements java.io.Serializable {

	// 主键
	private Long id;
	// 申报表id
	private Long declarationId;
	// 学校名称
	private String schoolName;
	// 教学科目
	private String subject;
	// 备注
	private String note;
	// 起始时间
	private String dateBegin;
	// 终止时间
	private String dateEnd;
	// 显示顺序
	private Integer sort;
	//个人资料id
	private String perId;

	// 构造器

	/** default constructor */
	public DecTeachExp() {
	}

	
	public DecTeachExp(Long id) {
		super();
		this.id = id;
	}


	/** full constructor */
	public DecTeachExp(Long declarationId, String schoolName, String subject,
			String note, String dateBegin, String dateEnd, Integer sort) {
		this.declarationId = declarationId;
		this.schoolName = schoolName;
		this.subject = subject;
		this.note = note;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
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


	public String getSchoolName() {
		return schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getDateBegin() {
		return dateBegin;
	}


	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}


	public String getDateEnd() {
		return dateEnd;
	}


	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
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
				+ ", schoolName:" + schoolName + ", subject:" + subject
				+ ", note:" + note + ", dateBegin:" + dateBegin + ", dateEnd:"
				+ dateEnd + ", sort:" + sort + "}";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}
}