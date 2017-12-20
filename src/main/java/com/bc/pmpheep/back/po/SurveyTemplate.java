package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：模版实体类
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
@Alias("SurveyTemplate")
public class SurveyTemplate implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = -7095331687874274350L;
    private Long              id;
    private String            templateName;
    private Integer           sort;

    // Constructors

    /** default constructor */
    public SurveyTemplate() {
    }

    /** full constructor */
    public SurveyTemplate(String templateName, Integer sort) {
        this.templateName = templateName;
        this.sort = sort;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}