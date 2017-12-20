package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：发起问卷实体类
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
@Alias("SurveyQuestionnaireStaging")
public class SurveyQuestionnaireStaging implements java.io.Serializable {

    //
    private static final long serialVersionUID = -4748731876186620073L;
    // Fields

    private Long              id;
    private Long              userId;
    private Long              questionnaireId;
    private Long              orgId;

    // Constructors

    /** default constructor */
    public SurveyQuestionnaireStaging() {
    }

    /** full constructor */
    public SurveyQuestionnaireStaging(Long userId, Long questionnaireId, Long orgId) {
        this.userId = userId;
        this.questionnaireId = questionnaireId;
        this.orgId = orgId;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionnaireId() {
        return this.questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}