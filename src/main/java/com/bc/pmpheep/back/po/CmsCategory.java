package com.bc.pmpheep.back.po;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：CMS 内容类别实体
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
@Alias("CmsCategory")
public class CmsCategory implements java.io.Serializable {

    // 主键
    private Long        id;
    // 上级类别id
    private Long        parentId;
    // 根节点路径
    private String      path;
    // 类别名称
    private String      categoryName;
    // 是否后台类别
    private Boolean     isBackground;
    // 操作权限id
    // private Long permissionId;
    // 是否需要审核
    private Boolean     isAuthRequired;
    // 审核角色id
    // private Long authRoleId;
    // 是否教材通知
    private Boolean     isMaterialNotice;
    // 是否显示摘要
    private Boolean     isSummaryVisible;
    // 是否显示关键字
    private Boolean     isKeywordVisible;
    // 是否显示作者
    private Boolean     isAuthorVisible;
    // 是否显示点击数
    private Boolean     isClicksVisible;
    // 是否允许评论
    private Boolean     isCommentsAllow;
    // 是否显示评论数
    private Boolean     isCommentsVisible;
    // 是否显示点赞数
    private Boolean     isLikesVisible;
    // 是否显示收藏数
    private Boolean     isBookmarksVisible;
    // 显示顺序
    private Integer     sort;

    private CmsCategory parentMenu;

    List<CmsCategory>   children;
    List<CmsCategory>   children1;

    private boolean     hasMenu = false;

    // Constructors

    /** default constructor */
    public CmsCategory() {
    }

    public CmsCategory(Long id, String path, String categoryName) {
        this.id = id;
        this.path = path;
        this.categoryName = categoryName;
    }

    public CmsCategory(Long parentId, String path, String categoryName, Boolean isBackground) {
        this.parentId = parentId;
        this.path = path;
        this.categoryName = categoryName;
        this.isBackground = isBackground;
    }

    /** full constructor */
    public CmsCategory(Long parentId, String path, String categoryName, Boolean isBackground,
    Boolean isAuthRequired, Boolean isMaterialNotice, Boolean isSummaryVisible,
    Boolean isKeywordVisible, Boolean isAuthorVisible, Boolean isClicksVisible,
    Boolean isCommentsAllow, Boolean isCommentsVisible, Boolean isLikesVisible,
    Boolean isBookmarksVisible, Integer sort) {
        this.parentId = parentId;
        this.path = path;
        this.categoryName = categoryName;
        this.isBackground = isBackground;
        // this.permissionId = permissionId;
        this.isAuthRequired = isAuthRequired;
        // this.authRoleId = authRoleId;
        this.isMaterialNotice = isMaterialNotice;
        this.isSummaryVisible = isSummaryVisible;
        this.isKeywordVisible = isKeywordVisible;
        this.isAuthorVisible = isAuthorVisible;
        this.isClicksVisible = isClicksVisible;
        this.isCommentsAllow = isCommentsAllow;
        this.isCommentsVisible = isCommentsVisible;
        this.isLikesVisible = isLikesVisible;
        this.isBookmarksVisible = isBookmarksVisible;
        this.sort = sort;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getIsBackground() {
        return this.isBackground;
    }

    public void setIsBackground(Boolean isBackground) {
        this.isBackground = isBackground;
    }

    // public Long getPermissionId() {
    // return this.permissionId;
    // }
    //
    // public void setPermissionId(Long permissionId) {
    // this.permissionId = permissionId;
    // }

    public Boolean getIsAuthRequired() {
        return this.isAuthRequired;
    }

    public void setIsAuthRequired(Boolean isAuthRequired) {
        this.isAuthRequired = isAuthRequired;
    }

    // public Long getAuthRoleId() {
    // return this.authRoleId;
    // }
    //
    // public void setAuthRoleId(Long authRoleId) {
    // this.authRoleId = authRoleId;
    // }

    public Boolean getIsMaterialNotice() {
        return this.isMaterialNotice;
    }

    public void setIsMaterialNotice(Boolean isMaterialNotice) {
        this.isMaterialNotice = isMaterialNotice;
    }

    public Boolean getIsSummaryVisible() {
        return this.isSummaryVisible;
    }

    public void setIsSummaryVisible(Boolean isSummaryVisible) {
        this.isSummaryVisible = isSummaryVisible;
    }

    public Boolean getIsKeywordVisible() {
        return this.isKeywordVisible;
    }

    public void setIsKeywordVisible(Boolean isKeywordVisible) {
        this.isKeywordVisible = isKeywordVisible;
    }

    public Boolean getIsAuthorVisible() {
        return this.isAuthorVisible;
    }

    public void setIsAuthorVisible(Boolean isAuthorVisible) {
        this.isAuthorVisible = isAuthorVisible;
    }

    public Boolean getIsClicksVisible() {
        return this.isClicksVisible;
    }

    public void setIsClicksVisible(Boolean isClicksVisible) {
        this.isClicksVisible = isClicksVisible;
    }

    public Boolean getIsCommentsAllow() {
        return this.isCommentsAllow;
    }

    public void setIsCommentsAllow(Boolean isCommentsAllow) {
        this.isCommentsAllow = isCommentsAllow;
    }

    public Boolean getIsCommentsVisible() {
        return this.isCommentsVisible;
    }

    public void setIsCommentsVisible(Boolean isCommentsVisible) {
        this.isCommentsVisible = isCommentsVisible;
    }

    public Boolean getIsLikesVisible() {
        return this.isLikesVisible;
    }

    public void setIsLikesVisible(Boolean isLikesVisible) {
        this.isLikesVisible = isLikesVisible;
    }

    public Boolean getIsBookmarksVisible() {
        return this.isBookmarksVisible;
    }

    public void setIsBookmarksVisible(Boolean isBookmarksVisible) {
        this.isBookmarksVisible = isBookmarksVisible;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the parentMenu
     */
    public CmsCategory getParentMenu() {
        return parentMenu;
    }

    /**
     * @param parentMenu the parentMenu to set
     */
    public void setParentMenu(CmsCategory parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * @return the children
     */
    public List<CmsCategory> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<CmsCategory> children) {
        this.children = children;
    }

    /**
     * @return the hasMenu
     */
    public boolean isHasMenu() {
        return hasMenu;
    }

    /**
     * @param hasMenu the hasMenu to set
     */
    public void setHasMenu(boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    /**
     * @return the children1
     */
    public List<CmsCategory> getChildren1() {
        return children1;
    }

    /**
     * @param children1 the children1 to set
     */
    public void setChildren1(List<CmsCategory> children1) {
        this.children1 = children1;
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
        return " {id:" + id + ", parentId:" + parentId + ", path:" + path + ", categoryName:"
               + categoryName + ", isBackground:" + isBackground + ", isAuthRequired:"
               + isAuthRequired + ", isMaterialNotice:" + isMaterialNotice + " , isSummaryVisible:"
               + isSummaryVisible + ", isKeywordVisible:" + isKeywordVisible + ", isAuthorVisible:"
               + isAuthorVisible + ", isClicksVisible:" + isClicksVisible + ", isCommentsAllow:"
               + isCommentsAllow + ", isCommentsVisible:" + isCommentsVisible + ", isLikesVisible:"
               + isLikesVisible + ", isBookmarksVisible:" + isBookmarksVisible + ", sort:" + sort
               + "}";
    }
}