package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 临床决策产品
 */
@SuppressWarnings("serial")
@Alias("Product")
public class Product implements Serializable {
    private Long id	;
    private String product_name	;
    private Long product_type	; //类型 1.人卫临床助手 2.人卫用药助手 3.人卫中医助手
    private Boolean is_published	;//是否已发布
    private Boolean is_unit_advise_used	;//所在单位意见是否启用
    private Boolean is_unit_advise_required	;// 所在单位意见是否必填
    private Boolean is_edu_exp_used	;// 主要学习经历是否启用
    private Boolean  is_edu_exp_required	;// 主要学习经历是否必填
    private Boolean is_work_exp_used	;// 	主要工作经历是否启用
    private Boolean is_work_exp_required	;// 	主要工作经历是否必填
    private Boolean is_acade_used	;// 主要学术兼职是否启用
    private Boolean is_acade_required;// 主要学术兼职是否必填
    private Boolean is_pmph_textbook_used	;// 	人卫社教材编写情况是否启用
    private Boolean is_pmph_textbook_required	;// 人卫社教材编写情况是否必填
    private Boolean is_monograph_used	;// 主编学术专著情况 是否启用
    private Boolean is_monograph_required	;// 主编学术专著情况 是否必填
    private Boolean is_edit_book_used	;// 	主编或参编图书情况 是否起用
    private Boolean is_edit_book_required	;// 主编或参编图书情况 是否必填
    private Boolean is_deleted	;// 是否被逻辑删除
    private Timestamp gmt_create	;//创建时间
    private Timestamp gmt_update	;//更新时间
    private Long founder_id	;//创建人id
    private Long publisher_id;//最后一次发布操作的社内用户id
    private Timestamp gmt_publish	;//最后一次发布时间
    private String note	;//备注
    private String description ;//产品简介
    private Date actualDeadline; //报名截止时间

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Long getProduct_type() {
        return product_type;
    }

    public void setProduct_type(Long product_type) {
        this.product_type = product_type;
    }

    public Boolean getIs_published() {
        return is_published;
    }

    public void setIs_published(Boolean is_published) {
        this.is_published = is_published;
    }

    public Boolean getIs_unit_advise_used() {
        return is_unit_advise_used;
    }

    public void setIs_unit_advise_used(Boolean is_unit_advise_used) {
        this.is_unit_advise_used = is_unit_advise_used;
    }

    public Boolean getIs_unit_advise_required() {
        return is_unit_advise_required;
    }

    public void setIs_unit_advise_required(Boolean is_unit_advise_required) {
        this.is_unit_advise_required = is_unit_advise_required;
    }

    public Boolean getIs_edu_exp_used() {
        return is_edu_exp_used;
    }

    public void setIs_edu_exp_used(Boolean is_edu_exp_used) {
        this.is_edu_exp_used = is_edu_exp_used;
    }

    public Boolean getIs_edu_exp_required() {
        return is_edu_exp_required;
    }

    public void setIs_edu_exp_required(Boolean is_edu_exp_required) {
        this.is_edu_exp_required = is_edu_exp_required;
    }

    public Boolean getIs_work_exp_used() {
        return is_work_exp_used;
    }

    public void setIs_work_exp_used(Boolean is_work_exp_used) {
        this.is_work_exp_used = is_work_exp_used;
    }

    public Boolean getIs_work_exp_required() {
        return is_work_exp_required;
    }

    public void setIs_work_exp_required(Boolean is_work_exp_required) {
        this.is_work_exp_required = is_work_exp_required;
    }

    public Boolean getIs_acade_used() {
        return is_acade_used;
    }

    public void setIs_acade_used(Boolean is_acade_used) {
        this.is_acade_used = is_acade_used;
    }

    public Boolean getIs_acade_required() {
        return is_acade_required;
    }

    public void setIs_acade_required(Boolean is_acade_required) {
        this.is_acade_required = is_acade_required;
    }

    public Boolean getIs_pmph_textbook_used() {
        return is_pmph_textbook_used;
    }

    public void setIs_pmph_textbook_used(Boolean is_pmph_textbook_used) {
        this.is_pmph_textbook_used = is_pmph_textbook_used;
    }

    public Boolean getIs_pmph_textbook_required() {
        return is_pmph_textbook_required;
    }

    public void setIs_pmph_textbook_required(Boolean is_pmph_textbook_required) {
        this.is_pmph_textbook_required = is_pmph_textbook_required;
    }

    public Boolean getIs_monograph_used() {
        return is_monograph_used;
    }

    public void setIs_monograph_used(Boolean is_monograph_used) {
        this.is_monograph_used = is_monograph_used;
    }

    public Boolean getIs_monograph_required() {
        return is_monograph_required;
    }

    public void setIs_monograph_required(Boolean is_monograph_required) {
        this.is_monograph_required = is_monograph_required;
    }

    public Boolean getIs_edit_book_used() {
        return is_edit_book_used;
    }

    public void setIs_edit_book_used(Boolean is_edit_book_used) {
        this.is_edit_book_used = is_edit_book_used;
    }

    public Boolean getIs_edit_book_required() {
        return is_edit_book_required;
    }

    public void setIs_edit_book_required(Boolean is_edit_book_required) {
        this.is_edit_book_required = is_edit_book_required;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
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

    public Long getFounder_id() {
        return founder_id;
    }

    public void setFounder_id(Long founder_id) {
        this.founder_id = founder_id;
    }

    public Long getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Long publisher_id) {
        this.publisher_id = publisher_id;
    }

    public Timestamp getGmt_publish() {
        return gmt_publish;
    }

    public void setGmt_publish(Timestamp gmt_publish) {
        this.gmt_publish = gmt_publish;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getActualDeadline() {
        return actualDeadline;
    }

    public void setActualDeadline(Date actualDeadline) {
        this.actualDeadline = actualDeadline;
    }
}
