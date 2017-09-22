package com.bc.pmpheep.back.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecCourseConstruction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_course_construction", catalog = "pmph_imesp_db")
public class DecCourseConstruction implements java.io.Serializable {

	// Fields

	private Long id;
	private Long declarationId;
	private String courseName;
	private String classHour;
	private Short type;
	private String note;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DecCourseConstruction() {
	}

	/** minimal constructor */
	public DecCourseConstruction(Long declarationId, String courseName,
			String classHour, Short type, Integer sort) {
		this.declarationId = declarationId;
		this.courseName = courseName;
		this.classHour = classHour;
		this.type = type;
		this.sort = sort;
	}

	/** full constructor */
	public DecCourseConstruction(Long declarationId, String courseName,
			String classHour, Short type, String note, Integer sort) {
		this.declarationId = declarationId;
		this.courseName = courseName;
		this.classHour = classHour;
		this.type = type;
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

	@Column(name = "course_name", nullable = false, length = 50)
	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Column(name = "class_hour", nullable = false, length = 50)
	public String getClassHour() {
		return this.classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	@Column(name = "type", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
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