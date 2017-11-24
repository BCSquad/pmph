package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 教材备注附件表
 * 
 * @introduction
 * 
 * @author Mryang
 * 
 * @createDate 2017年10月24日 下午5:23:00
 * 
 */
@SuppressWarnings("serial")
@Alias("MaterialNoteAttachment")
public class MaterialNoteAttachment implements java.io.Serializable {
    /**
     * 主键
     */
    private Long   id;
    /**
     * 教材通知备注id
     */
    private Long   materialExtraId;
    /**
     * 备注内容附件
     */
    private String attachment;
    /**
     * 附件名称
     */
    private String attachmentName;
    /**
     * 下载次数
     */
    private Long   download;

    public MaterialNoteAttachment() {
        super();
    }

    /**
     * <pre>
     * 构造器描述：
     *
     * @param id
     * @param materialExtraId
     * @param attachment
     * @param attachmentName
     * @param download
     *</pre>
     */
    public MaterialNoteAttachment(Long materialExtraId, String attachment, String attachmentName,
    Long download) {
        super();
        this.materialExtraId = materialExtraId;
        this.attachment = attachment;
        this.attachmentName = attachmentName;
        this.download = download;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialExtraId() {
        return materialExtraId;
    }

    public void setMaterialExtraId(Long materialExtraId) {
        this.materialExtraId = materialExtraId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Long getDownload() {
        return download;
    }

    public void setDownload(Long download) {
        this.download = download;
    }

    @Override
    public String toString() {
        return "{id:" + id + ", materialExtraId:" + materialExtraId + ", attachment:" + attachment
               + ", attachmentName:" + attachmentName + ", download:" + download + "}";
    }

}