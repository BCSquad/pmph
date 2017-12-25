package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

/**
 * 模版VO
 * @author tyc
 *
 */
@Alias("SurveyTemplateListVO")
public class SurveyTemplateVO implements java.io.Serializable {

    private static final long serialVersionUID = -7095331687874274350L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 模版名称
     */
    private String templateName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 模板创建人
     */
    private Long userId;
    /**
     * 问题id
     */
    private Long questionId;

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

	@Override
	public String toString() {
		return "SurveyTemplateListVO [id=" + id + ", templateName="
				+ templateName + ", sort=" + sort + ", userId=" + userId
				+ ", questionId=" + questionId + "]";
	}
}