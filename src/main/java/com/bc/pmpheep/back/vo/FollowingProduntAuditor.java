package com.bc.pmpheep.back.vo;

/**
 * 下属中的产品审核人
 */
@SuppressWarnings("serial")
public class FollowingProduntAuditor {

    private String fullPath; // 审核人部门全路径
    private String realname; // 审核人真实姓名
    private String productName; // 产品名称
    private Long productType; //产品类型
    private Long productId; //产品id
    private Long auditorId; // 审核人id


    public FollowingProduntAuditor() {
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }
}
