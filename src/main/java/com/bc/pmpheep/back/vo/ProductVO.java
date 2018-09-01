package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.back.po.ProductAuditor;
import com.bc.pmpheep.back.po.ProductAttachment;
import com.bc.pmpheep.back.po.ProductExtension;
import com.bc.pmpheep.general.po.Content;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 临床决策-数据传输类
 */
public class ProductVO {
    /**
    * 主键
     */
    private Long id	;
    private String product_name	;
    private Long product_type	; //类型 1.人卫临床助手 2.人卫用药助手 3.人卫中医助手
    private Boolean is_published;//是否已发布
    private Boolean is_unit_advise_used	;//所在单位意见是否启用
    private Boolean is_unit_advise_required	;// 所在单位意见是否必填
    private Boolean is_edu_exp_used	;// 主要学习经历是否启用
    private Boolean  is_edu_exp_required	;// 主要学习经历是否必填
    private Boolean is_work_exp_used	;// 	主要工作经历是否启用
    private Boolean is_work_exp_required	;// 	主要工作经历是否必填
    private Boolean is_acade_used	;// 主要学术兼职是否启用
    private Boolean is_acade_required ;// 主要学术兼职是否必填
    private Boolean is_pmph_textbook_used	;// 	人卫社教材编写情况是否启用
    private Boolean is_pmph_textbook_required	;// 人卫社教材编写情况是否必填
    private Boolean is_monograph_used	;// 主编学术专著情况 是否启用
    private Boolean is_monograph_required	;// 主编学术专著情况 是否必填
    private Boolean is_edit_book_used	;// 	主编或参编图书情况 是否起用
    private Boolean is_edit_book_required	;// 主编或参编图书情况 是否必填

    private Boolean	is_article_published_used	; //	文章发表情况 是否启用
    private Boolean	is_article_published_required	; //	文章发表情况 是否必填
    private Boolean	is_subject_type_used	; //	学科分类 是否启用
    private Boolean	is_subject_type_required	; //	学科分类 是否必填
    private Boolean	is_content_type_used	; //	内容分类 是否启用
    private Boolean	is_content_type_required	; //	内容分类 是否必填
    private Boolean	is_profession_type_used	; //	专业分类 是否启用
    private Boolean	is_profession_type_required	; //	专业分类 是否必填
    private Boolean	is_profession_award_used	; //	本专业获奖情况 是否启用
    private Boolean	is_profession_award_required	; //	本专业获奖情况 是否必填

    private Boolean is_deleted	;// 是否被逻辑删除
    private Timestamp gmt_create	;//创建时间
    private Timestamp gmt_update	;//更新时间
    private Timestamp gmt_set_active ; //设置为前台生效的时间。只有此时间最新的在前台展示
    private Long founder_id	;//创建人id
    private Long publisher_id;//最后一次发布操作的社内用户id
    private Timestamp gmt_publish	;//最后一次发布时间
    private String note	;//备注
    private String description ;//产品简介

    private Boolean is_active; // 是否前台生效
    private Content noteContent ; // 备注mongo内容实体类
    private Content descriptionContent ; // 产品简介mongo内容实体类
    private String publisher; // 发布人姓名
    private String founder; // 创建人姓名
    private String founderDepartment; //创建人部门名称
    private List<ProductAuditor> auditorList; //审核人列表
    private List<ProductExtension> ProductExtensionList; //扩展项列表
    private List<ProductAttachment> ProductAttachmentList; //附件列表
    private List<ProductAttachment> ProducntImgList; //上传图片列表

    public ProductVO() {

    }
    public ProductVO(Long productId, boolean b) {
        this.id = productId;
        this.is_published = b;
    }

    public ProductVO(Long product_type) {
        this.product_type = product_type;
        //1.人卫临床助手 2.人卫用药助手 3.人卫中医助手
        switch (String.valueOf(product_type)){
            case "1":
                this.product_name = "人卫临床助手";
                break;
            case "2":
                this.product_name = "人卫用药助手";
                break;
            case "3":
                this.product_name = "人卫中医助手";
                break;
            default:
                this.product_name ="未命名";
        }

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

    public List<ProductAuditor> getAuditorList() {
        return auditorList;
    }

    public void setAuditorList(List<ProductAuditor> auditorList) {
        for (ProductAuditor productAuditor:auditorList) {
            productAuditor.setProduct_id(id);
        }
        this.auditorList = auditorList;
    }

    public List<ProductExtension> getProductExtensionList() {
        return ProductExtensionList;
    }

    public void setProductExtensionList(List<ProductExtension> productExtensionList) {
        for (ProductExtension productExtension:productExtensionList) {
            productExtension.setProductId(id);
        }
        ProductExtensionList = productExtensionList;
    }

    public List<ProductAttachment> getProductAttachmentList() {
        return ProductAttachmentList;
    }

    public void setProductAttachmentList(List<ProductAttachment> productAttachmentList) {
        for (ProductAttachment productAttachment:productAttachmentList) {
            productAttachment.setProduct_id(id);
        }
        ProductAttachmentList = productAttachmentList;
    }

    public List<ProductAttachment> getProducntImgList() {
        return ProducntImgList;
    }

    public void setProducntImgList(List<ProductAttachment> producntImgList) {
        for (ProductAttachment  productAttachment:producntImgList) {
            productAttachment.setProduct_id(id);
        }
        ProducntImgList = producntImgList;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Content getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(Content noteContent) {
        this.noteContent = noteContent;
    }

    public Content getDescriptionContent() {
        return descriptionContent;
    }

    public void setDescriptionContent(Content descriptionContent) {
        this.descriptionContent = descriptionContent;
    }

    public Boolean getIs_article_published_used() {
        return is_article_published_used;
    }

    public void setIs_article_published_used(Boolean is_article_published_used) {
        this.is_article_published_used = is_article_published_used;
    }

    public Boolean getIs_article_published_required() {
        return is_article_published_required;
    }

    public void setIs_article_published_required(Boolean is_article_published_required) {
        this.is_article_published_required = is_article_published_required;
    }

    public Boolean getIs_subject_type_used() {
        return is_subject_type_used;
    }

    public void setIs_subject_type_used(Boolean is_subject_type_used) {
        this.is_subject_type_used = is_subject_type_used;
    }

    public Boolean getIs_subject_type_required() {
        return is_subject_type_required;
    }

    public void setIs_subject_type_required(Boolean is_subject_type_required) {
        this.is_subject_type_required = is_subject_type_required;
    }

    public Boolean getIs_content_type_used() {
        return is_content_type_used;
    }

    public void setIs_content_type_used(Boolean is_content_type_used) {
        this.is_content_type_used = is_content_type_used;
    }

    public Boolean getIs_content_type_required() {
        return is_content_type_required;
    }

    public void setIs_content_type_required(Boolean is_content_type_required) {
        this.is_content_type_required = is_content_type_required;
    }

    public Boolean getIs_profession_type_used() {
        return is_profession_type_used;
    }

    public void setIs_profession_type_used(Boolean is_profession_type_used) {
        this.is_profession_type_used = is_profession_type_used;
    }

    public Boolean getIs_profession_type_required() {
        return is_profession_type_required;
    }

    public void setIs_profession_type_required(Boolean is_profession_type_required) {
        this.is_profession_type_required = is_profession_type_required;
    }

    public Boolean getIs_profession_award_used() {
        return is_profession_award_used;
    }

    public void setIs_profession_award_used(Boolean is_profession_award_used) {
        this.is_profession_award_used = is_profession_award_used;
    }

    public Boolean getIs_profession_award_required() {
        return is_profession_award_required;
    }

    public void setIs_profession_award_required(Boolean is_profession_award_required) {
        this.is_profession_award_required = is_profession_award_required;
    }

    public String getFounderDepartment() {
        return founderDepartment;
    }

    public void setFounderDepartment(String founderDepartment) {
        this.founderDepartment = founderDepartment;
    }

    public Timestamp getGmt_set_active() {
        return gmt_set_active;
    }

    public void setGmt_set_active(Timestamp gmt_set_active) {
        this.gmt_set_active = gmt_set_active;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
}
