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
@Alias("SurveyQuestionCategory")
public class SurveyQuestionCategory implements java.io.Serializable {

    //
    private static final long serialVersionUID = 4924004454194440620L;
    // Fields
    /**
     * 主键
     */
    private Long              id;
    /**
     * 分类名称
     */
    private String            categoryName;
    /**
     * 排序
     */
    private Integer           sort;

    // Constructors

    // Constructors

    /** default constructor */
    public SurveyQuestionCategory() {
    }

    /** full constructor */
    public SurveyQuestionCategory(String categoryName, Integer sort) {
        this.categoryName = categoryName;
        this.sort = sort;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

}