package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 临床决策-内容分类
 */
public class ProductType implements Serializable{
    private Long id	;
    private Long parent_id ;//备用字段，当前要求学科分类无层级关系
    private String type_name; //50长度 名称
    private Long product_id; //产品id


    private int typeType; // 1.学科分类 2.内容分类 3.专业分类
    private Timestamp gmt_create;
    private Timestamp gmt_update;

    private Long productType ; // 产品分类
    private List<ProductType> childType; //子分类列表
    private String fullNamePath; //从顶级分类名称到自身分类名称的全名称路径 分隔符为 '/'

    public ProductType() {
    }

    public ProductType(Long productType){
        super();
        this.productType = productType;
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

    public List<ProductType> getChildType() {
        if(childType==null){
            childType = new ArrayList<ProductType>();
        }
        return childType;
    }

    public void setChildType(List<ProductType> childType) {
        this.childType = childType;
    }

    public String getFullNamePath() {
        return fullNamePath;
    }

    public void setFullNamePath(String fullNamePath) {
        this.fullNamePath = fullNamePath;
    }

    public Timestamp getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Timestamp gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Timestamp getGmt_update() {
        return gmt_update;
    }

    public void setGmt_update(Timestamp gmt_update) {
        this.gmt_update = gmt_update;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }


}
