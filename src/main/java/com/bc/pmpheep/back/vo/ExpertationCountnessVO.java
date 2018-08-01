package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

public class ExpertationCountnessVO {
    @ExcelHeader(header = "学科分类")
    private String typeName;
    @ExcelHeader(header = "申报人数")
    private String countSubmit;
    @ExcelHeader(header = "通过人数")
    private String countSuccess;

    public ExpertationCountnessVO() {
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCountSubmit() {
        return countSubmit;
    }

    public void setCountSubmit(String countSubmit) {
        this.countSubmit = countSubmit;
    }

    public String getCountSuccess() {
        return countSuccess;
    }

    public void setCountSuccess(String countSuccess) {
        this.countSuccess = countSuccess;
    }
}
