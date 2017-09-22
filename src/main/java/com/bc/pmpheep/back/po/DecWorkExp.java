package com.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecWorkExp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_work_exp", catalog = "pmph_imesp_db")
public class DecWorkExp implements java.io.Serializable {

	// Fields

	private Long id;
	private Long declarationId;
	private String orgName;
	private String position;
	private String note;
	private String dateBegin;
	private String dateEnd;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DecWorkExp() {
	}

	/** minimal constructor */
	public DecWorkExp(Long declarationId, String orgName, String position,
			String dateBegin, String dateEnd, Integer sort) {
		this.declarationId = declarationId;
		this.orgName = orgName;
		this.position = position;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.sort = sort;
	}

	/** full constructor */
	public DecWorkExp(Long declarationId, String orgName, String position,
			String note, String dateBegin, String dateEnd, Integer sort) {
		this.declarationId = declarationId;
		this.orgName = orgName;
		this.position = position;
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

	@Column(name = "org_name", nullable = false, length = 100)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "position", nullable = false, length = 100)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
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