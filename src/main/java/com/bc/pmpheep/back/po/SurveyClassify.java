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
@Alias("SurveyClassify")
public class SurveyClassify implements java.io.Serializable {

    //
    private static final long serialVersionUID = 4924004454194440620L;
    // Fields

    private Long              id;
    private String            classifyName;
    private Integer           sort;

    // Constructors

    /** default constructor */
    public SurveyClassify() {
    }

    /** full constructor */
    public SurveyClassify(String classifyName, Integer sort) {
        this.classifyName = classifyName;
        this.sort = sort;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassifyName() {
        return this.classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}