package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * SurveyQuestion entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * <pre>
 * 功能描述：问题实体类
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
@Alias("SurveyQuestion")
public class SurveyQuestion implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 6476408942311873206L;
    private Long              id;
    private String            title;
    private String            type;
    private Integer           sort;
    private String            direction;
    private Boolean           isAnswer;

    // Constructors

    /** default constructor */
    public SurveyQuestion() {
    }

    /** minimal constructor */
    public SurveyQuestion(String title, String type, Integer sort, Boolean isAnswer) {
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.isAnswer = isAnswer;
    }

    /** full constructor */
    public SurveyQuestion(String title, String type, Integer sort, String direction,
    Boolean isAnswer) {
        this.title = title;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
        this.isAnswer = isAnswer;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getIsAnswer() {
        return this.isAnswer;
    }

    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

}