package com.bc.pmpheep.general.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "messageAttachment")
public class MessageAttachment {
    @Id
    private String id;
    private String msgId;
    private String attachment;
    private String attachmentName;

    public MessageAttachment() {
        super();
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
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
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

}
