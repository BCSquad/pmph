package com.bc.pmpheep.back.po;

/**
 * 临床决策-扩展项
 */
public class ProductExtension {
    private Long id	;//主键	BIGINT(20)
    private Long productId	;//产品id	bigint(20)
    private String extensionName	;//扩展项名称	varchar(100)
    private Boolean isRequired	;//是否必填	tinyint(1)
    private Boolean isDeleted	;//是否被删除	tinyint(1)

    public ProductExtension() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        isDeleted = isDeleted;
    }
}
