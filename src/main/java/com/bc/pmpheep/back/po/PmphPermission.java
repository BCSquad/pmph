package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PmphPermission实体类
 * 
 * @author 曾庆峰
 * 
 */
@Alias("PmphPermission")
public class PmphPermission implements Serializable {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 上级许可id
     */
    private Long    parentId;
    /**
     * 根节点路径
     */
    private String  path;
    /**
     * 许可名称
     */
    private String  peermissionName;
    /**
     * 菜单名称
     */
    private String  menuName;
    /**
     * 相对地址
     */
    private String  url;
    /**
     * 是否禁用
     */
    private boolean isDisabled;
    /**
     * 备注
     */
    private String  note;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date    gmtCreate;
    /**
     * 修改时间
     */
    private Date    gmtUpdate;

    public PmphPermission() {
    }

    public PmphPermission(Long parentId, String path, String peermissionName, String menuName,
    String url, boolean isDisabled, String note, Integer sort, Date gmtCreate, Date gmtUpdate) {
        this.parentId = parentId;
        this.path = path;
        this.peermissionName = peermissionName;
        this.menuName = menuName;
        this.url = url;
        this.isDisabled = isDisabled;
        this.note = note;
        this.sort = sort;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the isDisabled
     */
    public boolean isDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled the isDisabled to set
     */
    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPeermissionName() {
        return peermissionName;
    }

    public void setPeermissionName(String peermissionName) {
        this.peermissionName = peermissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "PmphPermission [id=" + id + ", parentId=" + parentId + ", path=" + path
               + ", peermissionName=" + peermissionName + ", menuName=" + menuName + ", url=" + url
               + ", isDisabled=" + isDisabled + ", note=" + note + ", sort=" + sort
               + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
    }

}
