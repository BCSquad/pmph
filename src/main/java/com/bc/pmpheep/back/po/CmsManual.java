package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * CmsManual entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * <pre>
 * 功能描述：CMS帮助管理
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-3-8
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("CmsManual")
public class CmsManual implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = -8160102269438957213L;
    private Long              id;
    private String            manualName;
    private String            attachment;
    private String            note;
    private Long              download;
    private Long              userId;
    private Timestamp         gmtCreate;

    // Constructors

    /** default constructor */
    public CmsManual() {
    }

    /** full constructor */
    public CmsManual(String manualName, String attachment, String note, Long download, Long userId,
    Timestamp gmtCreate) {
        this.manualName = manualName;
        this.attachment = attachment;
        this.note = note;
        this.download = download;
        this.userId = userId;
        this.gmtCreate = gmtCreate;
    }

    public CmsManual(String manualName, String attachment, String note, Long userId) {
        this.manualName = manualName;
        this.attachment = attachment;
        this.note = note;
        this.userId = userId;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManualName() {
        return this.manualName;
    }

    public void setManualName(String manualName) {
        this.manualName = manualName;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getDownload() {
        return this.download;
    }

    public void setDownload(Long download) {
        this.download = download;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}