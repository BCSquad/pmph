package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

/**
 * 问题集合VO
 * @author tyc
 *
 */
@Alias("SurveyQuestionListVO")
public class SurveyQuestionListVO implements java.io.Serializable {

    private static final long serialVersionUID = 6476408942311873206L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 问题分类id
     */
    private Long categoryId;
    /**
     * 题目
     */
    private String title;
    /**
     * 问题类型
     */
    private Short type;
    /**
     * 问题序号
     */
    private Integer sort;
    /**
     * 问题说明
     */
    private String direction;
    /**
     * 问题是否必答
     */
    private Boolean isAnswer;
    /**
     * 选项内容
     */
    private String optionContent;
    /**
     * 是否有其他
     */
    private Boolean isOther;
    /**
     * 备注
     */
    private String remark;
    
    public SurveyQuestionListVO() {
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

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	public Boolean getIsOther() {
		return isOther;
	}

	public void setIsOther(Boolean isOther) {
		this.isOther = isOther;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SurveyQuestionListVO [id=" + id + ", categoryId=" + categoryId
				+ ", title=" + title + ", type=" + type + ", sort=" + sort
				+ ", direction=" + direction + ", isAnswer=" + isAnswer
				+ ", optionContent=" + optionContent + ", isOther=" + isOther
				+ ", remark=" + remark + "]";
	}
}