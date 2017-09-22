package com.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecEduExp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_edu_exp", catalog = "pmph_imesp_db")
public class DecEduExp implements java.io.Serializable {

	// Fields

	private Long id;
	private Long declarationId;
	private String schoolName;
	private String major;
	private String degree;
	private String note;
	private String dateBegin;
	private String dateEnd;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DecEduExp() {
	}

	/** minimal constructor */
	public DecEduExp(Long declarationId, String schoolName, String major,
			String degree, String dateBegin, String dateEnd, Integer sort) {
		this.declarationId = declarationId;
		this.schoolName = schoolName;
		this.major = major;
		this.degree = degree;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.sort = sort;
	}

	/** full constructor */
	public DecEduExp(Long declarationId, String schoolName, String major,
			String degree, String note, String dateBegin, String dateEnd,
			Integer sort) {
		this.declarationId = declarationId;
		this.schoolName = schoolName;
		this.major = major;
		this.degree = degree;
		this.note = note;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.sort = sort;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "declaration_id", nullable = false)
	public Long getDeclarationId() {
		return this.declarationId;
	}

	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}

	@Column(name = "school_name", nullable = false, length = 80)
	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Column(name = "major", nullable = false, length = 50)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "degree", nullable = false, length = 30)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "date_begin", nullable = false, length = 20)
	public String getDateBegin() {
		return this.dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	@Column(name = "date_end", nullable = false, length = 20)
	public String getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Column(name = "sort", nullable = false)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}