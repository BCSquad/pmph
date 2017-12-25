package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;


/**
 * 
 * 功能描述：问题实体类VO
 * @author tyc
 */
@Alias("SurveyQuestionVO")
public class SurveyQuestionVO implements java.io.Serializable {

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
     * 是否被逻辑删除
     */
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp gmtUpdate;
    /**
     * 条件分页总条数分页查询
     */
    private Integer count;
    /**
     * 页面查询条件（状态）
     */
    private Integer status;

    /** default constructor */
    public SurveyQuestionVO() {
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SurveyQuestionVO [id=" + id + ", categoryId=" + categoryId
				+ ", title=" + title + ", type=" + type + ", sort=" + sort
				+ ", direction=" + direction + ", isAnswer=" + isAnswer
				+ ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
				+ ", gmtUpdate=" + gmtUpdate + ", count=" + count + ", status="
				+ status + "]";
	}

}