package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("MaterialSurveyType")
public class MaterialSurveyType implements Serializable {
    private Long id;
    private String surveyName;
    private Integer sort;


    public MaterialSurveyType() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "MaterialSurveyType{" +
                "id=" + id +
                ", surveyName='" + surveyName + '\'' +
                ", sort=" + sort +
                '}';
    }
}
