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
@Alias("SurveyQuestionnaire")
public class SurveyQuestionnaire implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 1190785458692294163L;
    private Long              id;
    private String            title;
    private String            subhead;
    private String            intro;
    private Boolean           templateId;
    private Long              surveyId;
    private Long              userId;
    private Short             createrType;
    private Timestamp         gmtCreate;
    private Timestamp         gmtUpdate;
    private Timestamp         questionnaireStartDate;
    private Timestamp         questionnaireEndDate;
    private Integer           sort;

    // Constructors

    /** default constructor */
    public SurveyQuestionnaire() {
    }

    /** minimal constructor */
    public SurveyQuestionnaire(String title, String intro, Boolean templateId, Long surveyId,
    Long userId, Short createrType, Timestamp gmtCreate, Timestamp gmtUpdate,
    Timestamp questionnaireStartDate, Timestamp questionnaireEndDate, Integer sort) {
        this.title = title;
        this.intro = intro;
        this.templateId = templateId;
        this.surveyId = surveyId;
        this.userId = userId;
        this.createrType = createrType;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.questionnaireStartDate = questionnaireStartDate;
        this.questionnaireEndDate = questionnaireEndDate;
        this.sort = sort;
    }

    /** full constructor */
    public SurveyQuestionnaire(String title, String subhead, String intro, Boolean templateId,
    Long surveyId, Long userId, Short createrType, Timestamp gmtCreate, Timestamp gmtUpdate,
    Timestamp questionnaireStartDate, Timestamp questionnaireEndDate, Integer sort) {
        this.title = title;
        this.subhead = subhead;
        this.intro = intro;
        this.templateId = templateId;
        this.surveyId = surveyId;
        this.userId = userId;
        this.createrType = createrType;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.questionnaireStartDate = questionnaireStartDate;
        this.questionnaireEndDate = questionnaireEndDate;
        this.sort = sort;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubhead() {
        return this.subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getIntro() {
        return this.intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Boolean getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Boolean templateId) {
        this.templateId = templateId;
    }

    public Long getSurveyId() {
        return this.surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getCreaterType() {
        return this.createrType;
    }

    public void setCreaterType(Short createrType) {
        this.createrType = createrType;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return this.gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Timestamp getQuestionnaireStartDate() {
        return this.questionnaireStartDate;
    }

    public void setQuestionnaireStartDate(Timestamp questionnaireStartDate) {
        this.questionnaireStartDate = questionnaireStartDate;
    }

    public Timestamp getQuestionnaireEndDate() {
        return this.questionnaireEndDate;
    }

    public void setQuestionnaireEndDate(Timestamp questionnaireEndDate) {
        this.questionnaireEndDate = questionnaireEndDate;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}