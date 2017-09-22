package com.bc.pmpheep.back.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecTeachExp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_teach_exp", catalog = "pmph_imesp_db")
public class DecTeachExp implements java.io.Serializable {

    // Fields

    private Long    id;
    private Long    declarationId;
    private String  schoolName;
    private String  subject;
    private String  note;
    private String  dateBegin;
    private String  dateEnd;
    private Integer sort;

    // Constructors

    /** default constructor */
    public DecTeachExp() {
    }

    /** minimal constructor */
    public DecTeachExp(Long declarationId, String schoolName, String subject, String dateBegin,
    String dateEnd, Integer sort) {
        this.declarationId = declarationId;
        this.schoolName = schoolName;
        this.subject = subject;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.sort = sort;
    }

    /** full constructor */
    public DecTeachExp(Long declarationId, String schoolName, String subject, String note,
    String dateBegin, String dateEnd, Integer sort) {
        this.declarationId = declarationId;
        this.schoolName = schoolName;
        this.subject = subject;
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

    @Column(name = "school_name", nullable = false, length = 100)
    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Column(name = "subject", nullable = false, length = 150)
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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