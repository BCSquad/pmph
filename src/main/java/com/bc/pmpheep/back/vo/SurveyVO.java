package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 问卷实体类VO
 * 
 * @author tyc
 */
@Alias("SurveyVO")
public class SurveyVO implements java.io.Serializable {
	
    private static final long serialVersionUID = 1190785458692294163L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 问卷标题
     */
    private String title;
    /**
     * 问卷副标题
     */
    private String subhead;
    /**
     * 问卷简介
     */
    private String intro;
    /**
     * 问卷模版类型
     */
    private Long templateId;
    /**
     * 问卷调查类型
     */
    private Long typeId;
    /**
     * 问卷创建人
     */
    private Long userId;
    /**
     * 问卷开始时间
     */
    private Timestamp beginDate;
    /**
     * 问卷结束时间
     */
    private Timestamp endDate;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否被逻辑删除
     */
    private Boolean isDeleted;
    /**
     * 问卷创建时间
     */
    private Timestamp gmtCreate;
    /**
     * 问卷修改时间
     */
    private Timestamp gmtUpdate;
    /**
     * 条件分页总条数分页查询
     */
    private Integer count;
    /**
     * 页面查询条件（状态）
     */
    private Integer status;
    /**
     * 问卷调查类型名称
     */
    private String surveyName;
    /**
     * 用户名
     */
    private String username;

    /** default constructor */
    public SurveyVO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "SurveyVO [id=" + id + ", title=" + title + ", subhead="
				+ subhead + ", intro=" + intro + ", templateId=" + templateId
				+ ", typeId=" + typeId + ", userId=" + userId + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", sort=" + sort
				+ ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
				+ ", gmtUpdate=" + gmtUpdate + ", count=" + count + ", status="
				+ status + ", surveyName=" + surveyName + ", username="
				+ username + "]";
	}
}