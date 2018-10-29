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
    /**
     * 简介
     */
    private String            intro;
    /**
     * 调查类型
     */
    private Long              typeId;

    private Boolean isActive;

    private Long preVersionMaterialId;
    private String preVersionMaterialName;
    private String preVersionMaterialRound;



    // Constructors

    /** default constructor */
    public SurveyTemplate() {
    }

    public SurveyTemplate(Long id, Boolean isDeleted) {
        this.id = id;
        this.isDeleted = isDeleted;
    }

    /** full constructor */
    public SurveyTemplate(String templateName, Integer sort, Long userId, String intro,
    Long typeId, Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.templateName = templateName;
        this.sort = sort;
        this.userId = userId;
        this.intro = intro;
        this.typeId = typeId;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public SurveyTemplate(String templateName, String intro, Long typeId, Long userId) {
        this.templateName = templateName;
        this.intro = intro;
        this.typeId = typeId;
        this.userId = userId;
    }

    public SurveyTemplate(Long id, String templateName, String intro, Long typeId) {
        this.id = id;
        this.templateName = templateName;
        this.intro = intro;
        this.typeId = typeId;
    }
    
    public SurveyTemplate(String templateName, Integer sort, Long userId, String intro,
    	    Long typeId, Boolean isDeleted) {
        this.templateName = templateName;
        this.sort = sort;
        this.userId = userId;
        this.intro = intro;
        this.typeId = typeId;
        this.isDeleted = isDeleted;
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

    /**
     * @return the intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro the intro to set
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return the typeId
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }



    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public String getPreVersionMaterialRound() {
        return preVersionMaterialRound;
    }

    public void setPreVersionMaterialRound(String preVersionMaterialRound) {
        this.preVersionMaterialRound = preVersionMaterialRound;
    }

    /*@Override
    public String toString() {
        return "SurveyTemplate [id=" + id + ", templateName=" + templateName + ", sort=" + sort
               + ", userId=" + userId + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
               + ", gmtUpdate=" + gmtUpdate + "]";
    }*/

    @Override
    public String toString() {
        return "SurveyTemplate[" +
                "id=" + id +
                ", templateName='" + templateName + '\'' +
                ", sort=" + sort +
                ", userId=" + userId +
                ", isDeleted=" + isDeleted +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", intro='" + intro + '\'' +
                ", typeId=" + typeId +
                ", preVersionMaterialId=" + preVersionMaterialId +
                ", preVersionMaterialName='" + preVersionMaterialName + '\'' +
                ", preVersionMaterialRound='" + preVersionMaterialRound + '\'' +
                ']';
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}