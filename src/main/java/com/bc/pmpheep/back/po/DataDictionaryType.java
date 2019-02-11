package com.bc.pmpheep.back.po;


import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("DataDictionaryType")
public class DataDictionaryType implements Serializable {
    /**
     * 主键
     */
    private Long id ;
    /**
     * 字典类型业务code
     */
    private String typeCode ;
    /**
     * 字典类型名称
     */
    private String typeName ;
    /**
     * 备注
     */
    private String note ;
    /**
     * 排序
     */
    private Integer sort ;
    /**
     * 是否已删除
     */
    private Boolean deleted ;

    public DataDictionaryType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
