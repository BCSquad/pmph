package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 临床决策-附件
 */
@SuppressWarnings("serial")
@Alias("ProductProfessionType")
public class ProductProfessionType implements Serializable {
    private Long id	;//主键	bigint
    private Long parentId	;//产品主键	bigint
    private String typeName	;//附件在mongdb上的id	varchar
    private Boolean isDeleted	;//附件名称	varchar
    private Long productId;

    public ProductProfessionType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
