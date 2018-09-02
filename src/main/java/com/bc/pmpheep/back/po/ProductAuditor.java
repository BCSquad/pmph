package com.bc.pmpheep.back.po;

/**
 * 临床决策 - 审核人
 */
public class ProductAuditor {
    private Long id	;//主键
    private Long product_id ;//	产品id
    private Long auditor_id	;//审核人id,关联pmph_user

    private String auditor_name ; //审核人姓名

    public ProductAuditor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getAuditor_id() {
        return auditor_id;
    }

    public void setAuditor_id(Long auditor_id) {
        this.auditor_id = auditor_id;
    }

    public String getAuditor_name() {
        return auditor_name;
    }

    public void setAuditor_name(String auditor_name) {
        this.auditor_name = auditor_name;
    }
}
