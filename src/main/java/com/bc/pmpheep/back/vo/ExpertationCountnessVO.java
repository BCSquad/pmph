package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

public class ExpertationCountnessVO {
    @ExcelHeader(header = "学科分类")
    private String typeName;
    @ExcelHeader(header = "申报人数")
    private Long countSubmit;
    @ExcelHeader(header = "通过人数")
    private Long countSuccess;

    public ExpertationCountnessVO() {
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public Long getCountSubmit() {
        return countSubmit;
    }

    public void setCountSubmit(Long countSubmit) {
        this.countSubmit = countSubmit;
    }

    public Long getCountSuccess() {
        return countSuccess;
    }

    public void setCountSuccess(Long countSuccess) {
        this.countSuccess = countSuccess;
    }
}
