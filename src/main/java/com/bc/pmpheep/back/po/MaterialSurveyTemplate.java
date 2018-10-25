package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 *  调研表模板实体类
 */
@Alias("MaterialSurveyTemplate")
public class MaterialSurveyTemplate implements java.io.Serializable {

    /**
     * 主键
     */
    private Long id ;
    /**
     * 模版名称
     */
    private String templateName ;
    /**
     * 显示顺序
     */
    private Integer sort ;
    /**
     * 模板创建人(0=系统)
     */
    private Long userId ;
    /**
     * 是否被逻辑删除
     */
    private Boolean isDeleted ;
    /**
     * 是否有效
     */
    private Boolean isActive ;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate ;
    /**
     * 修改时间
     */
    private Timestamp gmtUpdate ;
    /**
     * 概述
     */
    private String intro ;
    /**
     * 问卷调查类型
     */
    private Long typeId ;

    private Long preVersionMaterialId ;

    private String preVersionMaterialName ;

    private Integer preVersionMaterialRound ;


    public MaterialSurveyTemplate() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getPreVersionMaterialId() {
        return preVersionMaterialId;
    }

    public void setPreVersionMaterialId(Long preVersionMaterialId) {
        this.preVersionMaterialId = preVersionMaterialId;
    }

    public String getPreVersionMaterialName() {
        return preVersionMaterialName;
    }

    public void setPreVersionMaterialName(String preVersionMaterialName) {
        this.preVersionMaterialName = preVersionMaterialName;
    }

    public Integer getPreVersionMaterialRound() {
        return preVersionMaterialRound;
    }

    public void setPreVersionMaterialRound(Integer preVersionMaterialRound) {
        this.preVersionMaterialRound = preVersionMaterialRound;
    }
}