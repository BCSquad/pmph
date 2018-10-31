package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 模版实体类VO分页
 * 
 * @author tyc
 */
@Alias("SurveyTemplateListVO")
public class SurveyTemplateListVO implements java.io.Serializable {

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
     * 简介
     */
    private String            intro;
    /**
     * 调查类型
     */
    private String            surveyName;
    /**
     * 排序
     */
    private Integer           sort;
    /**
     * 模板创建人
     */
    private Long              userId;
    /**
     * 问卷开始时间
     */
    private String            startTime;

    /**
     * 问卷结束时间
     */
    private String            endTime;
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
     * 条件分页总条数分页查询
     */
    private Integer           count;
    /**
     * 页面查询条件（状态）
     */
    private Integer           status;
    /**
     * 模版创建人
     */
    private String            username;
    /**
     * 模版创建人
     */
    private String            realname;
    /**
     * 问卷开始时间
     */
    private Timestamp         beginDate;
    /**
     * 问卷结束时间
     */
    private Timestamp         endDate;

    private Boolean            isActive ;

    /** default constructor */
    public SurveyTemplateListVO() {
    }

    public SurveyTemplateListVO(Long id, String templateName, String intro, String surveyName, Integer sort, Long userId, String startTime, String endTime, Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate, Integer count, Integer status, String username, String realname, Timestamp beginDate, Timestamp endDate, Boolean isActive) {
        this.id = id;
        this.templateName = templateName;
        this.intro = intro;
        this.surveyName = surveyName;
        this.sort = sort;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.count = count;
        this.status = status;
        this.username = username;
        this.realname = realname;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return the surveyName
     */
    public String getSurveyName() {
        return surveyName;
    }

    /**
     * @param surveyName the surveyName to set
     */
    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }




    @Override
    public String toString() {
        return "SurveyTemplateListVO [id=" + id + ", templateName=" + templateName + ", sort="
               + sort + ", userId=" + userId + ", isDeleted=" + isDeleted + ", gmtCreate="
               + gmtCreate + ", gmtUpdate=" + gmtUpdate + ", count=" + count + ", status=" + status
               + "]";
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
}