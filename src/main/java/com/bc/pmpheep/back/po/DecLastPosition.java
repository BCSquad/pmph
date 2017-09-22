package com.bc.pmpheep.back.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DecLastPosition entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dec_last_position", catalog = "pmph_imesp_db")
public class DecLastPosition implements java.io.Serializable {

    // Fields

    private Long    id;
    private Long    declarationId;
    private String  materialName;
    private Short   position;
    private String  note;
    private Integer sort;

    // Constructors

    /** default constructor */
    public DecLastPosition() {
    }

    /** minimal constructor */
    public DecLastPosition(Long declarationId, String materialName, Short position, Integer sort) {
        this.declarationId = declarationId;
        this.materialName = materialName;
        this.position = position;
        this.sort = sort;
    }

    /** full constructor */
    public DecLastPosition(Long declarationId, String materialName, Short position, String note,
    Integer sort) {
        this.declarationId = declarationId;
        this.materialName = materialName;
        this.position = position;
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

    @Column(name = "position", nullable = false)
    public Short getPosition() {
        return this.position;
    }

    public void setPosition(Short position) {
        this.position = position;
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