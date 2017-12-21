package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问卷调查类型实体类
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
@Alias("SurveyType")
public class SurveyType implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = -6852705084817228169L;
    private Long              id;
    private String            surveyName;
    private Integer           sort;

    // Constructors

    /** default constructor */
    public SurveyType() {
    }

    /** full constructor */
    public SurveyType(String surveyName, Integer sort) {
        this.surveyName = surveyName;
        this.sort = sort;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurveyName() {
        return this.surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}