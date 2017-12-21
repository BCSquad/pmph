package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问题回答实体类
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
@Alias("SurveyAnswer")
public class SurveyAnswer implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 8608362154676560544L;
    private Long              id;
    private Long              userId;
    private Long              questionId;
    private Long              optionsId;
    private String            optionContent;
    private String            attachment;
    private Timestamp         gmtCreate;

    // Constructors

    /** default constructor */
    public SurveyAnswer() {
    }

    /** full constructor */
    public SurveyAnswer(Long userId, Long questionId, Long optionsId, String optionContent,
    String attachment, Timestamp gmtCreate) {
        this.userId = userId;
        this.questionId = questionId;
        this.optionsId = optionsId;
        this.optionContent = optionContent;
        this.attachment = attachment;
        this.gmtCreate = gmtCreate;
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

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getOptionsId() {
        return this.optionsId;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId = optionsId;
    }

    public String getOptionContent() {
        return this.optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}