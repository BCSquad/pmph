package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：CMS定时发布内容表（发布后删除条目） 实体
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
@Alias("CmsSchedule")
public class CmsSchedule implements java.io.Serializable {

    // Fields
    // 主键
    private Long      id;
    // 内容id
    private Long      contentId;
    // 定时发布时间
    private Timestamp scheduledTime;

    // Constructors

    /** default constructor */
    public CmsSchedule() {
    }

    public CmsSchedule(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public CmsSchedule(Long id, Long contentId, Timestamp scheduledTime) {
        this.id = id;
        this.contentId = contentId;
        this.scheduledTime = scheduledTime;
    }

    /** full constructor */
    public CmsSchedule(Long contentId, Timestamp scheduledTime) {
        this.contentId = contentId;
        this.scheduledTime = scheduledTime;
    }

    // Property accessors
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

    public Timestamp getScheduledTime() {
        return this.scheduledTime;
    }

    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
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
        return "{id=" + id + ", contentId=" + contentId + ", scheduledTime=" + scheduledTime + "}";
    }

}