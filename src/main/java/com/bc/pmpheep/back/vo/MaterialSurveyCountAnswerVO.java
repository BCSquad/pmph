package com.bc.pmpheep.back.vo;


import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 调研表结果统计列表项实体类
 */
@Alias("MaterialSurveyCountAnswerVO")
public class MaterialSurveyCountAnswerVO implements Serializable {
    private Long surveyId;
    private String realname;
    private Long orgId;
    private String orgName;
    private Integer userType;
    private String userTypeName;
    private Timestamp SubmitTime;
    private Integer count;

    public MaterialSurveyCountAnswerVO() {
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public Timestamp getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        SubmitTime = submitTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
