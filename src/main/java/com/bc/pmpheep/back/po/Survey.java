package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问卷实体类
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
@Alias("Survey")
public class Survey implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 1190785458692294163L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 问卷标题
     */
    private String            title;
    /**
     * 问卷副标题
     */
    private String            subhead;
    /**
     * 问卷简介
     */
    private String            intro;
    /**
     * 问卷模版类型
     */
    private Long              templateId;
    /**
     * 问卷调查类型
     */
    private Long              typeId;
    /**
     * 问卷创建人
     */
    private Long              userId;
    /**
     * 问卷开始时间
     */
    private Timestamp         beginDate;
    /**
     * 问卷结束时间
     */
    private Timestamp         endDate;
    /**
     * 排序
     */
    private Integer           sort;
    /**
     * 是否被逻辑删除
     */
    private Boolean           isDeleted;
    /**
     * 问卷创建时间
     */
    private Timestamp         gmtCreate;
    /**
     * 问卷修改时间
     */
    private Timestamp         gmtUpdate;

    // Constructors

    /** default constructor */
    public Survey() {
    }

    /** minimal constructor */
    public Survey(String title, Long templateId, Long typeId, Long userId, Integer sort,
    Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.title = title;
        this.templateId = templateId;
        this.typeId = typeId;
        this.userId = userId;
        this.sort = sort;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    /** full constructor */
    public Survey(String title, String subhead, String intro, Long templateId, Long typeId,
    Long userId, Timestamp beginDate, Timestamp endDate, Integer sort, Boolean isDeleted,
    Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.title = title;
        this.subhead = subhead;
        this.intro = intro;
        this.templateId = templateId;
        this.typeId = typeId;
        this.userId = userId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sort = sort;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    // Property accessors
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the subhead
     */
    public String getSubhead() {
        return subhead;
    }

    /**
     * @param subhead the subhead to set
     */
    public void setSubhead(String subhead) {
        this.subhead = subhead;
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
     * @return the templateId
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
     * @return the beginDate
     */
    public Timestamp getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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