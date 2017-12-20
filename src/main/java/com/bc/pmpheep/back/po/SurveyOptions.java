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
@Alias("SurveyOptions")
public class SurveyOptions implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 7331413291591376250L;
    private Long              id;
    private Long              questionId;
    private String            optionContent;
    private Boolean           isOther;
    private String            remark;

    // Constructors

    /** default constructor */
    public SurveyOptions() {
    }

    /** full constructor */
    public SurveyOptions(Long questionId, String optionContent, Boolean isOther, String remark) {
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

}