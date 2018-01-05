package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

/**
 * 模版VO
 * 
 * @author tyc
 * 
 */
@Alias("SurveyTemplateVO")
public class SurveyTemplateVO implements java.io.Serializable {

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
    private Long              typeId;
    /**
     * 排序
     */
    private Integer           sort;
    /**
     * 模板创建人
     */
    private Long              userId;
    /**
     * 问题id
     */
    private Long              questionId;

    public SurveyTemplateVO() {
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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
     * @return the typeId
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "SurveyTemplateListVO [id=" + id + ", templateName=" + templateName + ", sort="
               + sort + ", userId=" + userId + ", questionId=" + questionId + "]";
    }
}