package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 模版实体类VO分页
 * 
 * @author tyc
 */
@Alias("SurveyTemplateListVO")
public class SurveyTemplateListVO implements java.io.Serializable {

    private static final long serialVersionUID = -7095331687874274350L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 模版名称
     */
    private String            templateName;
    /**
     * 排序
     */
    private Integer           sort;
    /**
     * 模板创建人
     */
    private Long              userId;
    /**
     * 是否被逻辑删除
     */
    private Boolean           isDeleted;
    /**
     * 创建时间
     */
    private Timestamp         gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp         gmtUpdate;
    /**
     * 条件分页总条数分页查询
     */
    private Integer           count;
    /**
     * 页面查询条件（状态）
     */
    private Integer           status;
    /**
     * 模版创建人
     */
    private String            username;

    /** default constructor */
    public SurveyTemplateListVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SurveyTemplateListVO [id=" + id + ", templateName=" + templateName + ", sort="
               + sort + ", userId=" + userId + ", isDeleted=" + isDeleted + ", gmtCreate="
               + gmtCreate + ", gmtUpdate=" + gmtUpdate + ", count=" + count + ", status=" + status
               + "]";
    }
}