package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

/**
 * 临床决策-内容分类
 */
public class ProductType {
    private Long id	;
    private Long parent_id ;//备用字段，当前要求学科分类无层级关系
    private String type_name; //50长度 名称
    private int typeType; // 1.学科分类 2.内容分类

    public ProductType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getTypeType() {
        return typeType;
    }

    public void setTypeType(int typeType) {
        this.typeType = typeType;
    }
}
