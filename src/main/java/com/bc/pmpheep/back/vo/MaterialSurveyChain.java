package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("MaterialSurveyChain")
public class MaterialSurveyChain implements Serializable{
    private	Long	id	;//	主键
    private	Long	materialSurveyId	;//	教材调研表id
    private	Long	materialId	;//	教材关联id
    private	Long	textbookId	;//	书籍关联id
    private	Boolean	required	;//	对作家用户必填(书籍关联的调研)

    private Long preVersionMaterialId ;

    private String preVersionMaterialName ;

    private Integer preVersionMaterialRound ;

    public MaterialSurveyChain() {
    }

    public MaterialSurveyChain(Long materialId, Long textbookId) {
        this.materialId = materialId;
        this.textbookId = textbookId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialSurveyId() {
        return materialSurveyId;
    }

    public void setMaterialSurveyId(Long materialSurveyId) {
        this.materialSurveyId = materialSurveyId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(Long textbookId) {
        this.textbookId = textbookId;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Long getPreVersionMaterialId() {
        return preVersionMaterialId;
    }

    public void setPreVersionMaterialId(Long preVersionMaterialId) {
        this.preVersionMaterialId = preVersionMaterialId;
    }

    public String getPreVersionMaterialName() {
        return preVersionMaterialName;
    }

    public void setPreVersionMaterialName(String preVersionMaterialName) {
        this.preVersionMaterialName = preVersionMaterialName;
    }

    public Integer getPreVersionMaterialRound() {
        return preVersionMaterialRound;
    }

    public void setPreVersionMaterialRound(Integer preVersionMaterialRound) {
        this.preVersionMaterialRound = preVersionMaterialRound;
    }

    @Override
    public String toString() {
        return "MaterialSurveyChain{" +
                "id=" + id +
                ", materialSurveyId=" + materialSurveyId +
                ", materialId=" + materialId +
                ", textbookId=" + textbookId +
                ", required=" + required +
                ", preVersionMaterialId=" + preVersionMaterialId +
                ", preVersionMaterialName='" + preVersionMaterialName + '\'' +
                ", preVersionMaterialRound=" + preVersionMaterialRound +
                '}';
    }
}
