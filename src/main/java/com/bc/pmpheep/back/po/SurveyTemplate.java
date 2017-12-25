package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：模版实体类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyTemplate")
public class SurveyTemplate implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = -7095331687874274350L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 模版名称
     */
    private String            templateName;
    /**
     * 排序
     */
    private Integer           sort;
    /**
     * 模板创建人
     */
    private Long              userId;
    /**
     * 是否被逻辑删除
     */
    private Boolean           isDeleted;
    /**
     * 创建时间
     */
    private Timestamp         gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp         gmtUpdate;

    // Constructors

    /** default constructor */
    public SurveyTemplate() {
    }

    /** full constructor */
    public SurveyTemplate(String templateName, Integer sort, Long userId, Boolean isDeleted,
    Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.templateName = templateName;
        this.sort = sort;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }
    
    public SurveyTemplate(String templateName, Integer sort, Long userId) {
        this.templateName = templateName;
        this.sort = sort;
        this.userId = userId;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the gmtCreate
     */
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the gmtUpdate
     */
    public Timestamp getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * @param gmtUpdate the gmtUpdate to set
     */
    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

}