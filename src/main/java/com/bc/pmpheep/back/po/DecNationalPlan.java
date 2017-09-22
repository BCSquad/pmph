package com.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecNationalPlan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_national_plan", catalog = "pmph_imesp_db")
public class DecNationalPlan implements java.io.Serializable {

	// Fields

	private Long id;
	private Long declarationId;
	private String materialName;
	private String isbn;
	private Short rank;
	private String note;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DecNationalPlan() {
	}

	/** minimal constructor */
	public DecNationalPlan(Long declarationId, String materialName, Short rank,
			Integer sort) {
		this.declarationId = declarationId;
		this.materialName = materialName;
		this.rank = rank;
		this.sort = sort;
	}

	/** full constructor */
	public DecNationalPlan(Long declarationId, String materialName,
			String isbn, Short rank, String note, Integer sort) {
		this.declarationId = declarationId;
		this.materialName = materialName;
		this.isbn = isbn;
		this.rank = rank;
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

	@Column(name = "material_name", nullable = false, length = 100)
	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@Column(name = "isbn", length = 50)
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "rank", nullable = false)
	public Short getRank() {
		return this.rank;
	}

	public void setRank(Short rank) {
		this.rank = rank;
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