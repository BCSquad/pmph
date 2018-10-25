package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问卷调查-类型问题选项VO
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyQuestionOptionCategoryVO")
public class SurveyQuestionOptionCategoryVO implements Serializable {
    //
    private static final long serialVersionUID = -8571933838375288824L;

    private Long id ;
    /**
     * 分类名称
     */
    private String            categoryName;
    /**
     * 问题分类id
     */
    private Long              categoryId;
    /**
     * 题目
     */
    private String            title;
    /**
     * 问题类型
     */
    private Short             type;
    /**
     * 问题序号
     */
    private Integer           sort;
    /**
     * 问题说明
     */
    private String            direction;
    /**
     * 问题是否必答
     */
    private Boolean           isAnswer;
    /**
     * 是否被逻辑删除
     */
    private Boolean           isDeleted;
    /**
     * 创建时间
     */
    private Timestamp         gmtCreate;

    /**
     * 选项主键
     */
    private Long              optionId;
    private String            optionIdString;
    /**
     * 问题表id
     */
    private Long              questionId;
    /**
     * 选项内容
     */
    private String            optionContent;
    /**
     * 是否有其他
     */
    private Boolean           isOther;
    /**
     * 备注
     */
    private String            remark;

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
     * @return the type
     */
    public Short getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the isAnswer
     */
    public Boolean getIsAnswer() {
        return isAnswer;
    }

    /**
     * @param isAnswer the isAnswer to set
     */
    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    /**
     * @return the isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
     * @return the isOther
     */
    public Boolean getIsOther() {
        return isOther;
    }

    /**
     * @param isOther the isOther to set
     */
    public void setIsOther(Boolean isOther) {
        this.isOther = isOther;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the optionIdString
     */
    public String getOptionIdString() {
        return optionIdString;
    }

    /**
     * @param optionIdString the optionIdString to set
     */
    public void setOptionIdString(String optionIdString) {
        this.optionIdString = optionIdString;
    }

	@Override
	public String toString() {
		return "SurveyQuestionOptionCategoryVO [categoryName=" + categoryName
				+ ", categoryId=" + categoryId + ", title=" + title + ", type="
				+ type + ", sort=" + sort + ", direction=" + direction
				+ ", isAnswer=" + isAnswer + ", isDeleted=" + isDeleted
				+ ", gmtCreate=" + gmtCreate + ", optionId=" + optionId
				+ ", optionIdString=" + optionIdString + ", questionId="
				+ questionId + ", optionContent=" + optionContent
				+ ", isOther=" + isOther + ", remark=" + remark + "]";
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getOther() {
        return isOther;
    }

    public void setOther(Boolean other) {
        isOther = other;
    }
}
