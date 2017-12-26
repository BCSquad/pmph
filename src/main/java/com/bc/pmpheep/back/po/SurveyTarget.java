package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：发起问卷实体类
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
@Alias("SurveyTarget")
public class SurveyTarget implements java.io.Serializable {

    //
    private static final long serialVersionUID = -4748731876186620073L;
    // Fields
    /**
     * 主键
     */
    private Long              id;
    /**
     * 发起人id
     */
    private Long              userId;
    /**
     * 问卷表Id
     */
    private Long              surveyId;
    /**
     * 机构id
     */
    private Long              orgId;
    /**
     * 发起时间
     */
    private Timestamp         gmtCreate;

    // Constructors

    /** default constructor */
    public SurveyTarget() {
    }

    public SurveyTarget(Long userId, Long surveyId, Long orgId) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.orgId = orgId;
    }

    /** full constructor */
    public SurveyTarget(Long userId, Long surveyId, Long orgId, Timestamp gmtCreate) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.orgId = orgId;
        this.gmtCreate = gmtCreate;
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
     * @return the surveyId
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

	@Override
	public String toString() {
		return "SurveyTarget [id=" + id + ", userId=" + userId + ", surveyId="
				+ surveyId + ", orgId=" + orgId + ", gmtCreate=" + gmtCreate
				+ "]";
	}

}