package com.bc.pmpheep.back.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.SurveyQuestionOption;

/**
 * 问题集合VO
 * 
 * @author tyc
 * 
 */
@Alias("SurveyQuestionListVO")
public class SurveyQuestionListVO {

    /**
     * 主键
     */
    private Long                       id;
    /**
     * 问题分类id
     */
    private Long                       categoryId;
    /**
     * 题目
     */
    private String                     title;
    /**
     * 问题类型
     */
    private Short                      type;
    /**
     * 问题序号
     */
    private Integer                    sort;
    /**
     * 问题说明
     */
    private String                     direction;
    /**
     * 问题是否必答
     */
    private Boolean                    isAnswer;

    private Boolean                    isDeleted;
    /**
     * 问题选项集合
     */
    private List<SurveyQuestionOption> surveyQuestionOptionList;

    public SurveyQuestionListVO() {
    }

    /**
     * <pre>
     * 构造器描述：
     *
     * @param id
     * @param categoryId
     * @param title
     * @param type
     * @param sort
     * @param direction
     * @param isAnswer
     * @param surveyQuestionOptionList
     *</pre>
     */
    public SurveyQuestionListVO(Long id, Long categoryId, String title, Short type, Integer sort,
    String direction, Boolean isAnswer, List<SurveyQuestionOption> surveyQuestionOptionList) {
        super();
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
        this.isAnswer = isAnswer;
        this.surveyQuestionOptionList = surveyQuestionOptionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public List<SurveyQuestionOption> getSurveyQuestionOptionList() {
        return surveyQuestionOptionList;
    }

    public void setSurveyQuestionOptionList(List<SurveyQuestionOption> surveyQuestionOptionList) {
        this.surveyQuestionOptionList = surveyQuestionOptionList;
    }

    /*@Override
    public String toString() {
        return "SurveyQuestionListVO [id=" + id + ", categoryId=" + categoryId + ", title=" + title
               + ", type=" + type + ", sort=" + sort + ", direction=" + direction + ", isAnswer="
               + isAnswer + ", surveyQuestionOptionList=" + surveyQuestionOptionList + "]";
    }*/

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
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "SurveyQuestionListVO[" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", sort=" + sort +
                ", direction='" + direction + '\'' +
                ", isAnswer=" + isAnswer +
                ", isDeleted=" + isDeleted +
                ", surveyQuestionOptionList=" + surveyQuestionOptionList +
                ']';
    }
}