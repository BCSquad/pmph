package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

/**
 * SurveyTemplate模版VO获取标题和简介
 * 
 * @author tyc
 */
@Alias("SurveyTemplateGetVO")
public class SurveyTemplateGetVO implements java.io.Serializable {

    private static final long serialVersionUID = -7095331687874274350L;
    /**
     * 模版主键
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

	public SurveyTemplateGetVO() {
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

	@Override
	public String toString() {
		return "SurveyTemplateGetVO [id=" + id + ", title=" + title
				+ ", subhead=" + subhead + ", intro=" + intro + "]";
	}

 
}