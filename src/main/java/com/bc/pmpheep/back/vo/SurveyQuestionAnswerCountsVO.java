package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问卷调查统计VO
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-27
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyQuestionAnswerCountsVO")
public class SurveyQuestionAnswerCountsVO implements Serializable {
    //
    private static final long serialVersionUID = -5909293452223028325L;
    /**
     * 问卷ID
     */
    private Long              surveyId;
    /**
     * 问卷名称
     */
    private String            surveyTitle;
    /**
     * 问卷调查对象
     */
    private String            surveyName;
    /**
     * 问卷概述
     */
    private String            intro;
    /**
     * 问卷发起人
     */
    private String            realname;
    /**
     * 问卷参与人数
     */
    private Integer           surveyUsers;
    /**
     * 问题ID
     */
    private Long              questionId;
    /**
     * 问题名称
     */
    private String            title;
    /**
     * 选项ID
     */
    private String            optionId;
    /**
     * 选项描述
     */
    private String            optionContent;
    /**
     * 每个选项选中次数
     */
    private String            optionCount;

    /**
     * 回答问题总数
     */
    private Integer           questionSum;
    /**
     * 开始时间
     */
    private Timestamp         startDate;
    /**
     * 结束时间
     */
    private Timestamp         endDate;
    /**
     * 问题排序
     */
    private Integer           questionSort;
    /**
     * 问题排序
     */
    private Integer           questionType;
    /**
     * 条件分页总条数
     */
    private Integer           count;

    /**
     * @return the questionId
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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
     * @return the optionId
     */
    public String getOptionId() {
        return optionId;
    }

    /**
     * @param optionId the optionId to set
     */
    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    /**
     * @return the optionContent
     */
    public String getOptionContent() {
        return optionContent;
    }

    /**
     * @param optionContent the optionContent to set
     */
    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    /**
     * @return the optionCount
     */
    public String getOptionCount() {
        return optionCount;
    }

    /**
     * @param optionCount the optionCount to set
     */
    public void setOptionCount(String optionCount) {
        this.optionCount = optionCount;
    }

    /**
     * @return the questionSum
     */
    public Integer getQuestionSum() {
        return questionSum;
    }

    /**
     * @param questionSum the questionSum to set
     */
    public void setQuestionSum(Integer questionSum) {
        this.questionSum = questionSum;
    }

    /**
     * @return the startDate
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
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
     * @return the surveyTitle
     */
    public String getSurveyTitle() {
        return surveyTitle;
    }

    /**
     * @param surveyTitle the surveyTitle to set
     */
    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
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

    /**
     * @return the surveyUsers
     */
    public Integer getSurveyUsers() {
        return surveyUsers;
    }

    /**
     * @param surveyUsers the surveyUsers to set
     */
    public void setSurveyUsers(Integer surveyUsers) {
        this.surveyUsers = surveyUsers;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the questionSort
     */
    public Integer getQuestionSort() {
        return questionSort;
    }

    /**
     * @param questionSort the questionSort to set
     */
    public void setQuestionSort(Integer questionSort) {
        this.questionSort = questionSort;
    }

    /**
     * @return the questionType
     */
    public Integer getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType the questionType to set
     */
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

}
