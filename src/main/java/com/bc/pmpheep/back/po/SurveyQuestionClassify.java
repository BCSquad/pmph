package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问题分类实体类
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
@Alias("SurveyQuestionClassify")
public class SurveyQuestionClassify implements java.io.Serializable {

    // Fields

    private Long id;
    private Long classifyId;
    private Long questionId;

    // Constructors

    /** default constructor */
    public SurveyQuestionClassify() {
    }

    /** full constructor */
    public SurveyQuestionClassify(Long classifyId, Long questionId) {
        this.classifyId = classifyId;
        this.questionId = questionId;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassifyId() {
        return this.classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

}