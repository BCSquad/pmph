package com.bc.pmpheep.back.po;

/**
 * 主编或参编图书情况
 */
public class DecEditorBook {

    private  Long id	;//主键
    private Long expertationId;//	临床申报id
    private String materialName;
    private String publisher;
    private String publishDate	;
    private String note	;
    private Integer sort;
    private String perId; //个人资料id


    public DecEditorBook() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpertationId() {
        return expertationId;
    }

    public void setExpertationId(Long expertationId) {
        this.expertationId = expertationId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPerId() {
        return perId;
    }

    public void setPerId(String perId) {
        this.perId = perId;
    }
}
