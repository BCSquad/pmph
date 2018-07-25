package com.bc.pmpheep.back.po;

/**
 * 临床决策-附件
 */
public class ProductAttachment {
    private Long id	;//主键	bigint
    private Long product_id	;//产品主键	bigint
    private String attachment	;//附件在mongdb上的id	varchar
    private String attachment_name	;//附件名称	varchar
    private Long download	;//下载次数	bigint
    private Boolean is_deleted	;//是否被删除	tinyint
    private Boolean is_scan_img	;//1- 扫描图片 0-附件	tinyint

    public ProductAttachment() {
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachment_name() {
        return attachment_name;
    }

    public void setAttachment_name(String attachment_name) {
        this.attachment_name = attachment_name;
    }

    public Long getDownload() {
        return download;
    }

    public void setDownload(Long download) {
        this.download = download;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Boolean getIs_scan_img() {
        return is_scan_img;
    }

    public void setIs_scan_img(Boolean is_scan_img) {
        this.is_scan_img = is_scan_img;
    }
}
