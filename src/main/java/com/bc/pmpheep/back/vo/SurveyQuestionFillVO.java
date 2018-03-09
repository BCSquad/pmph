package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.annotation.ExcelHeader;

/**
 * 
 * <pre>
 * 功能描述：问卷调查填空题统计
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-1-3
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyQuestionFillVO")
public class SurveyQuestionFillVO implements Serializable {

    //
    private static final long serialVersionUID = 8150117684173866753L;
    /**
     * 问卷ID
     */
    private Long              surveyId;
    /**
     * 用户id
     */
    private Long              userId;
    /**
     * 用户姓名
     */
    @ExcelHeader(header = "填写人")
    private String            realname;
    /**
     * 问题id
     */
    private Long              questionId;
    /**
     * 问题名称
     */
    private String            questionName;
    /**
     * 问题名称
     */
    private Integer           questionType;
    /**
     * 选项id
     */
    private Long              optionId;
    /**
     * 选项内容
     */
    @ExcelHeader(header = "调查结果")
    private String            optionContent;
    /**
     * 分页总数
     */
    private Integer           count;
    /**
     * 创建时间
     */
    @ExcelHeader(header = "时间")
    private String            gmtCreate;

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
     * @return the optionId
     */
    public Long getOptionId() {
        return optionId;
    }

    /**
     * @param optionId the optionId to set
     */
    public void setOptionId(Long optionId) {
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
     * @return the gmtCreate
     */
    public String getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @param questionName the questionName to set
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
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

}
