package com.bc.pmpheep.back.po;

import com.bc.pmpheep.back.util.ObjectUtil;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("DataDictionaryItem")
public class DataDictionaryItem implements Serializable{
    /**
     * 主键
     */
    private Long id ;
    /**
     * 字典类型主键
     */
    private Long typeId ;
    /**
     * 业务识别code建议以type_id加下划线开头
     */
    private String code ;
    /**
     * 字典项显示名称
     */
    private String name ;
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

    /**
     * 是否已删除的反字 数据库不存在 为绑定vue的checkbox存在
     */
    private Boolean unDeleted;

    public DataDictionaryItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.unDeleted = ObjectUtil.isNull(deleted)?null:!deleted;
    }

    public Boolean getUnDeleted() {
        return unDeleted;
    }

    public void setUnDeleted(Boolean unDeleted) {
        this.unDeleted = unDeleted;
    }
}
