package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：CMS内容收藏 实体
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
@Alias("CmsUserMark")
public class CmsUserMark implements java.io.Serializable {

    // Fields
    // 主键
    private Long      id;
    // 内容id
    private Long      contentId;
    // 用户id
    private Long      writerId;
    // 收藏夹id
    private Long      favoriteId;
    // 创建时间
    private Timestamp gmtCreate;

    // Constructors

    /** default constructor */
    public CmsUserMark() {
    }

    /** full constructor */
    public CmsUserMark(Long contentId, Long writerId, Long favoriteId, Timestamp gmtCreate) {
        this.contentId = contentId;
        this.writerId = writerId;
        this.favoriteId = favoriteId;
        this.gmtCreate = gmtCreate;
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

    public Long getWriterId() {
        return this.writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Long getFavoriteId() {
        return this.favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
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
        return "{id:" + id + ", contentId:" + contentId + ", writerId:" + writerId
               + ", favoriteId:" + favoriteId + ", gmtCreate:" + gmtCreate + "}";
    }

}