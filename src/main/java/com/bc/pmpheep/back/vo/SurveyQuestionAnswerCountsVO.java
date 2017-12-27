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

}
