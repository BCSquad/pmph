package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问题选项实体类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyQuestionOption")
public class SurveyQuestionOption implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 7331413291591376250L;
    /**
     * 主键
     */
    private Long              id;
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

    private Boolean           isDeleted ;

    // Constructors

    /** default constructor */
    public SurveyQuestionOption() {
    }

    /** full constructor */
    public SurveyQuestionOption(Long questionId, String optionContent, Boolean isOther,
    String remark) {
        this.questionId = questionId;
        this.optionContent = optionContent;
        this.isOther = isOther;
        this.remark = remark;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getOptionContent() {
        return this.optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Boolean getIsOther() {
        return this.isOther;
    }

    public void setIsOther(Boolean isOther) {
        this.isOther = isOther;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SurveyQuestionOption [id=" + id + ", questionId=" + questionId + ", optionContent="
               + optionContent + ", isOther=" + isOther + ", remark=" + remark + "]";
    }

    public Boolean getOther() {
        return isOther;
    }

    public void setOther(Boolean other) {
        isOther = other;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}