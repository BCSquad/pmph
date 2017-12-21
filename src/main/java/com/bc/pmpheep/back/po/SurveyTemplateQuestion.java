package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问题模版实体类
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
@Alias("SurveyTemplateQuestion")
public class SurveyTemplateQuestion implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = -1288144395734966965L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 模版表主键
     */
    private Long              templateId;
    /**
     * 问题表主键
     */
    private Long              questionId;

    // Constructors

    /** default constructor */
    public SurveyTemplateQuestion() {
    }

    /** full constructor */
    public SurveyTemplateQuestion(Long templateId, Long questionId) {
        this.templateId = templateId;
        this.questionId = questionId;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

}