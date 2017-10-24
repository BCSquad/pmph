package com.bc.pmpheep.back.po;
import org.apache.ibatis.type.Alias;

/**
 * CMS内容类别实体
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午4:51:52
 *
 */
@SuppressWarnings("serial")
@Alias("CmsCategory")
public class CmsCategory implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 上级类别id
	 */
	private Long parentId;
	/**
	 * 根节点路径
	 */
	private String path;
	/**
	 * 类别名称
	 */
	private String categoryName;
	/**
	 * 是否后台类别
	 */
	private Boolean isBackground;
	/**
	 * 操作权限id
	 */
	private Long permissionId;
	/**
	 * 是否需要审核
	 */
	private Boolean isAuthRequired;
	/**
	 * 审核角色id
	 */
	private Long authRoleId;
	/**
	 * 是否教材通知
	 */
	private Boolean isMaterialNotice;
	/**
	 * 教材id
	 */
	private Long materialId;
	/**
	 * 是否显示摘要
	 */
	private Boolean isSummaryVisible;
	/**
	 * 是否显示关键字
	 */
	private Boolean isKeywordVisible;
	/**
	 * 是否显示作者
	 */
	private Boolean isAuthorVisible;
	/**
	 * 是否显示点击数
	 */
	private Boolean isClicksVisible;
	/**
	 * 是否允许评论
	 */
	private Boolean isCommentsAllow;
	/**
	 * 是否显示评论数
	 */
	private Boolean isCommentsVisible;
	/**
	 * 是否显示点赞数
	 */
	private Boolean isLikesVisible;
	/**
	 * 是否显示收藏数
	 */
	private Boolean isBookmarksVisible;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	
	public CmsCategory() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Boolean getIsBackground() {
		return isBackground;
	}

	public void setIsBackground(Boolean isBackground) {
		this.isBackground = isBackground;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Boolean getIsAuthRequired() {
		return isAuthRequired;
	}

	public void setIsAuthRequired(Boolean isAuthRequired) {
		this.isAuthRequired = isAuthRequired;
	}

	public Long getAuthRoleId() {
		return authRoleId;
	}

	public void setAuthRoleId(Long authRoleId) {
		this.authRoleId = authRoleId;
	}

	public Boolean getIsMaterialNotice() {
		return isMaterialNotice;
	}

	public void setIsMaterialNotice(Boolean isMaterialNotice) {
		this.isMaterialNotice = isMaterialNotice;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Boolean getIsSummaryVisible() {
		return isSummaryVisible;
	}

	public void setIsSummaryVisible(Boolean isSummaryVisible) {
		this.isSummaryVisible = isSummaryVisible;
	}

	public Boolean getIsKeywordVisible() {
		return isKeywordVisible;
	}

	public void setIsKeywordVisible(Boolean isKeywordVisible) {
		this.isKeywordVisible = isKeywordVisible;
	}

	public Boolean getIsAuthorVisible() {
		return isAuthorVisible;
	}

	public void setIsAuthorVisible(Boolean isAuthorVisible) {
		this.isAuthorVisible = isAuthorVisible;
	}

	public Boolean getIsClicksVisible() {
		return isClicksVisible;
	}

	public void setIsClicksVisible(Boolean isClicksVisible) {
		this.isClicksVisible = isClicksVisible;
	}

	public Boolean getIsCommentsAllow() {
		return isCommentsAllow;
	}

	public void setIsCommentsAllow(Boolean isCommentsAllow) {
		this.isCommentsAllow = isCommentsAllow;
	}

	public Boolean getIsCommentsVisible() {
		return isCommentsVisible;
	}

	public void setIsCommentsVisible(Boolean isCommentsVisible) {
		this.isCommentsVisible = isCommentsVisible;
	}

	public Boolean getIsLikesVisible() {
		return isLikesVisible;
	}

	public void setIsLikesVisible(Boolean isLikesVisible) {
		this.isLikesVisible = isLikesVisible;
	}

	public Boolean getIsBookmarksVisible() {
		return isBookmarksVisible;
	}

	public void setIsBookmarksVisible(Boolean isBookmarksVisible) {
		this.isBookmarksVisible = isBookmarksVisible;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", parentId:" + parentId + ", path:" + path
				+ ", categoryName:" + categoryName + ", isBackground:"
				+ isBackground + ", permissionId:" + permissionId
				+ ", isAuthRequired:" + isAuthRequired + ", authRoleId:"
				+ authRoleId + ", isMaterialNotice:" + isMaterialNotice
				+ ", materialId:" + materialId + ", isSummaryVisible:"
				+ isSummaryVisible + ", isKeywordVisible:" + isKeywordVisible
				+ ", isAuthorVisible:" + isAuthorVisible + ", isClicksVisible:"
				+ isClicksVisible + ", isCommentsAllow:" + isCommentsAllow
				+ ", isCommentsVisible:" + isCommentsVisible
				+ ", isLikesVisible:" + isLikesVisible
				+ ", isBookmarksVisible:" + isBookmarksVisible + ", sort:"
				+ sort + "}";
	}
	
	

}