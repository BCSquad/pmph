package com.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecResearch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_research", catalog = "pmph_imesp_db")
public class DecResearch implements java.io.Serializable {

	// Fields

	private Long id;
	private Long declarationId;
	private String researchName;
	private String approvalUnit;
	private String award;
	private String note;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DecResearch() {
	}

	/** minimal constructor */
	public DecResearch(Long declarationId, String researchName,
			String approvalUnit, Integer sort) {
		this.declarationId = declarationId;
		this.researchName = researchName;
		this.approvalUnit = approvalUnit;
		this.sort = sort;
	}

	/** full constructor */
	public DecResearch(Long declarationId, String researchName,
			String approvalUnit, String award, String note, Integer sort) {
		this.declarationId = declarationId;
		this.researchName = researchName;
		this.approvalUnit = approvalUnit;
		this.award = award;
		this.note = note;
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

	@Column(name = "research_name", nullable = false, length = 150)
	public String getResearchName() {
		return this.researchName;
	}

	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}

	@Column(name = "approval_unit", nullable = false, length = 100)
	public String getApprovalUnit() {
		return this.approvalUnit;
	}

	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}

	@Column(name = "award", length = 100)
	public String getAward() {
		return this.award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "sort", nullable = false)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}