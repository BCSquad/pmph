package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * SurveyQuestion entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * <pre>
 * 功能描述：问题实体类
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
@Alias("SurveyQuestion")
public class SurveyQuestion implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 6476408942311873206L;
    /**
     * 主键
     */
    private Long              id;

    /**
     * 调研表id
     */
    private Long              surveyId;
    /**
     * 问题分类id
     */
    private Long              categoryId;
    /**
     * 题目
     */
    private String            title;
    /**
     * 问题类型
     */
    private Short             type;
    /**
     * 问题序号
     */
    private Integer           sort;
    /**
     * 问题说明
     */
    private String            direction;
    /**
     * 问题是否必答
     */
    private Boolean           isAnswer;
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

    /** default constructor */
    public SurveyQuestion() {
    }

    /** minimal constructor */
    public SurveyQuestion(Long categoryId, String title, Short type, Integer sort,
    Boolean isAnswer, Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.isAnswer = isAnswer;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    /** full constructor */
    public SurveyQuestion(Long categoryId, String title, Short type, Integer sort,
    String direction, Boolean isAnswer, Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
        this.isAnswer = isAnswer;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public SurveyQuestion(Long id, Long categoryId, String title, Short type, Integer sort,
    String direction, Boolean isAnswer) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
        this.isAnswer = isAnswer;
    }

    public SurveyQuestion(String title, Short type, Integer sort, String direction) {
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
    }
    
    public SurveyQuestion(Long categoryId, String title, Short type, Integer sort,
    	    String direction, Boolean isAnswer) {
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
        this.isAnswer = isAnswer;
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
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
     * @return the type
     */
    public Short getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Short type) {
        this.type = type;
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
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the isAnswer
     */
    public Boolean getIsAnswer() {
        return isAnswer;
    }

    /**
     * @param isAnswer the isAnswer to set
     */
    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
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
        return "SurveyQuestion [id=" + id + ", categoryId=" + categoryId + ", title=" + title
               + ", type=" + type + ", sort=" + sort + ", direction=" + direction + ", isAnswer="
               + isAnswer + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
               + ", gmtUpdate=" + gmtUpdate + "]";
    }



    public Boolean getAnswer() {
        return isAnswer;
    }

    public void setAnswer(Boolean answer) {
        isAnswer = answer;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
}