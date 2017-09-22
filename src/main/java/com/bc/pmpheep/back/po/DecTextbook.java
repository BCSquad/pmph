package com.bc.pmpheep.back.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DecTextbook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_textbook", catalog = "pmph_imesp_db")
public class DecTextbook implements java.io.Serializable {

    // Fields

    private Long    id;
    private Long    declarationId;
    private String  materialName;
    private Short   rank;
    private Short   position;
    private String  publisher;
    private Date    publishDate;
    private String  isbn;
    private String  note;
    private Integer sort;

    // Constructors

    /** default constructor */
    public DecTextbook() {
    }

    /** minimal constructor */
    public DecTextbook(Long declarationId, String materialName, Short rank, Short position,
    String publisher, Date publishDate, Integer sort) {
        this.declarationId = declarationId;
        this.materialName = materialName;
        this.rank = rank;
        this.position = position;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.sort = sort;
    }

    /** full constructor */
    public DecTextbook(Long declarationId, String materialName, Short rank, Short position,
    String publisher, Date publishDate, String isbn, String note, Integer sort) {
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

    @Column(name = "rank", nullable = false)
    public Short getRank() {
        return this.rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

    @Column(name = "position", nullable = false)
    public Short getPosition() {
        return this.position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    @Column(name = "publisher", nullable = false, length = 50)
    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date", nullable = false, length = 10)
    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Column(name = "isbn", length = 50)
    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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