package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：CMS内容-类别关联 实体
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
@Alias("CmsContentCategory")
public class CmsContentCategory implements java.io.Serializable {

    // 主键
    private Long id;
    // 内容id
    private Long contentId;
    // 内容类型
    private Long categoryId;

    // Constructors

    /** default constructor */
    public CmsContentCategory() {
    }

    /** full constructor */
    public CmsContentCategory(Long contentId, Long categoryId) {
        this.contentId = contentId;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        return "{id:" + id + ", contentId:" + contentId + ", categoryId:" + categoryId + "}";
    }

}