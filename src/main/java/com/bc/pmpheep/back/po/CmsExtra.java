package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：CMS内容附件 实体
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-25
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("serial")
@Alias("CmsExtra")
public class CmsExtra implements java.io.Serializable {
    // 主键
    private Long   id;
    // 内容id
    private Long   contentId;
    // 附件
    private String attachment;
    // 附件名称
    private String attachmentName;
    // 下载次数
    private Long   download;

    // Constructors

    /** default constructor */
    public CmsExtra() {
    }

    public CmsExtra(Long id, Long contentId, String attachment) {
        this.id = id;
        this.contentId = contentId;
        this.attachment = attachment;
    }

    /** full constructor */
    public CmsExtra(Long contentId, String attachment, String attachmentName, Long download) {
        this.contentId = contentId;
        this.attachment = attachment;
        this.attachmentName = attachmentName;
        this.download = download;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return this.contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentName() {
        return this.attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Long getDownload() {
        return this.download;
    }

    public void setDownload(Long download) {
        this.download = download;
    }

    /**
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @Override
    public String toString() {
        return "{id:" + id + ", contentId:" + contentId + ", attachment:" + attachment
               + ", attachmentName:" + attachmentName + ", download:" + download + "}";
    }

}