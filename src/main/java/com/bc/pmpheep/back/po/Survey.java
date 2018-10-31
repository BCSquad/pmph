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
     * 状态 0=未发送/1=发送/2=回收
     */
    private Short             status;
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

    private Long materialId;
    private Long preVersionMaterialId;
    private String preVersionMaterialName ;
    private Long preVersionMaterialRound ;

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

    public Survey(String title, String intro, Long templateId, Long typeId, Long userId) {
        this.title = title;
        this.intro = intro;
        this.templateId = templateId;
        this.typeId = typeId;
        this.userId = userId;
    }

    public Survey(Long id, String title, String intro, Long typeId) {
        this.id = id;
        this.title = title;
        this.typeId = typeId;
        this.intro = intro;
    }

    public Survey(Long id, Short status, Timestamp beginDate, Timestamp endDate) {
        this.id = id;
        this.status = status;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    /** full constructor */
    public Survey(String title, String subhead, String intro, Long templateId, Long typeId,
    Long userId, Timestamp beginDate, Timestamp endDate, Integer sort, Short status,
    Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.title = title;
        this.subhead = subhead;
        this.intro = intro;
        this.templateId = templateId;
        this.typeId = typeId;
        this.userId = userId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sort = sort;
        this.status = status;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public Survey(String title, String subhead, String intro, Long templateId, Long typeId,
    Long userId, Timestamp beginDate, Timestamp endDate, Integer sort, Short status) {
        this.title = title;
        this.subhead = subhead;
        this.intro = intro;
        this.templateId = templateId;
        this.typeId = typeId;
        this.userId = userId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sort = sort;
        this.status = status;
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
     * @return the status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Short status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Survey [id=" + id + ", title=" + title + ", subhead=" + subhead + ", intro="
               + intro + ", templateId=" + templateId + ", typeId=" + typeId + ", userId=" + userId
               + ", beginDate=" + beginDate + ", endDate=" + endDate + ", sort=" + sort
               + ", status=" + status + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
               + ", gmtUpdate=" + gmtUpdate + "]";
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

    public Long getPreVersionMaterialRound() {
        return preVersionMaterialRound;
    }

    public void setPreVersionMaterialRound(Long preVersionMaterialRound) {
        this.preVersionMaterialRound = preVersionMaterialRound;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
}