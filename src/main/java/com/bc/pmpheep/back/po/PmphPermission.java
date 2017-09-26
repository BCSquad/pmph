package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * PmphPermission实体类
 * 
 * @author 曾庆峰
 * 
 */
@SuppressWarnings("serial")
@Alias("PmphPermission")
public class PmphPermission implements Serializable {
    /**
     * 主键
     */
    private Long           id;
    /**
     * 上级许可id
     */
    private Long           parentId;
    /**
     * 根节点路径
     */
    private String         path;
    /**
     * 许可名称
     */
    private String         peermissionName;
    /**
     * 菜单名称
     */
    private String         menuName;
    /**
     * 相对地址
     */
    private String         url;
    /**
     * 是否禁用
     */
    private boolean        isDisabled;
    /**
     * 备注
     */
    private String         note;
    /**
     * 显示顺序
     */
    private Integer        sort;
    /**
     * 创建时间
     */
    private Timestamp      gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp      gmtUpdate;

    private PmphPermission parentMenu;
    List<PmphPermission>   children;
    private boolean        hasChild = false;

    public PmphPermission(Long id) {
        super();
        this.id = id;
    }

    public PmphPermission() {
    }

    /**
     * @param parentId
     * @param path
     * @param peermissionName
     * @param menuName
     * @param url
     * @param isDisabled
     * @param note
     * @param sort
     * @param gmtCreate
     * @param gmtUpdate
     */
    public PmphPermission(Long parentId, String path, String peermissionName, String menuName,
    String url, boolean isDisabled, String note, Integer sort, Timestamp gmtCreate,
    Timestamp gmtUpdate) {
        super();
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
     * @return the parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the peermissionName
     */
    public String getPeermissionName() {
        return peermissionName;
    }

    /**
     * @param peermissionName the peermissionName to set
     */
    public void setPeermissionName(String peermissionName) {
        this.peermissionName = peermissionName;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the gmtCreate
     */
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the gmtUpdate
     */
    public Timestamp getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * @param gmtUpdate the gmtUpdate to set
     */
    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    /**
     * @return the children
     */
    public List<PmphPermission> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<PmphPermission> children) {
        this.children = children;
    }

    /**
     * @return the parentMenu
     */
    public PmphPermission getParentMenu() {
        return parentMenu;
    }

    /**
     * @param parentMenu the parentMenu to set
     */
    public void setParentMenu(PmphPermission parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * @return the hasChild
     */
    public boolean isHasChild() {
        return hasChild;
    }

    /**
     * @param hasChild the hasChild to set
     */
    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PmphPermission [id=" + id + ", parentId=" + parentId + ", path=" + path
               + ", peermissionName=" + peermissionName + ", menuName=" + menuName + ", url=" + url
               + ", isDisabled=" + isDisabled + ", note=" + note + ", sort=" + sort
               + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
    }

}
