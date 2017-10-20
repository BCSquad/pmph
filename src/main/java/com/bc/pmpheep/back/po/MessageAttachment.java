package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：消息附件
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-19
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("MessageAttachment")
public class MessageAttachment {
    /**
     * 主键
     */
    private Long   id;
    /**
     * 消息ID
     */
    private String msgId;
    /**
     * 附件路径
     */
    private String attachment;
    /**
     * 附件名称
     */
    private String attachmentName;

    public MessageAttachment() {
        super();
    }

    public MessageAttachment(Long id, String attachment) {
        super();
        this.id = id;
        this.attachment = attachment;
    }

    /**
     * <pre>
     * 构造器描述：
     *
     * @param id
     * @param msgId
     * @param attachment
     * @param attachmentName
     *</pre>
     */
    public MessageAttachment(String msgId, String attachment, String attachmentName) {
        super();
        this.msgId = msgId;
        this.attachment = attachment;
        this.attachmentName = attachmentName;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId the msgId to set
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return the attachment
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName the attachmentName to set
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
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
        return "｛id:" + id + ", msgId:" + msgId + ", attachment:" + attachment
               + ", attachmentName:" + attachmentName + "｝";
    }

}
