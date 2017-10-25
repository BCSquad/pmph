package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * CMS内容 实体 
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:02:50
 *
 */
@SuppressWarnings("serial")
@Alias("CmsContent")
public class CmsContent implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 上级id
	 */
	private Long parentId;
	/**
	 * 根节点路径
	 */
	private String path;
	/**
	 * 内容id
	 */
	private String mid;
	/**
	 * 内容类型
	 */
	private Long categoryId;
	/**
	 * 内容标题
	 */
	private String title;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * 作者类型
	 */
	private Short authorType;
	/**
	 * 作者id
	 */
	private Long authorId;
	/**
	 * 点击数
	 */
	private Long clicks;
	/**
	 * 评论数
	 */
	private Long comments;
	/**
	 * 点赞数
	 */
	private Long likes;
	/**
	 * 收藏数
	 */
	private Long bookmarks;
	/**
	 * 是否分类置顶
	 */
	private Boolean isStick;
	/**
	 * 分类显示顺序
	 */
	private Integer sort;
	/**
	 * 置顶到期时间
	 */
	private Timestamp deadlineStick;
	/**
	 * 是否热门
	 */
	private Boolean isHot;
	/**
	 * 热门显示顺序
	 */
	private Integer sortHot;
	/**
	 * 热门到期时间
	 */
	private Timestamp deadlineHot;
	/**
	 * 是否推荐
	 */
	private Boolean isPromote;
	/**
	 * 推荐显示顺序
	 */
	private Integer sortPromote;
	/**
	 * 推荐到期时间
	 */
	private Timestamp deadlinePromote;
	/**
	 * 是否定时发布
	 */
	private Boolean isScheduled;
	/**
	 * 是否隐藏
	 */
	private Boolean isHide;
	/**
	 * 是否已发布
	 */
	private Boolean isPublished;
	/**
	 * 是否通过审核
	 */
	private Boolean isAuth;
	/**
	 * 审核者id
	 */
	private Long authUserId;
	/**
	 * 审核通过时间
	 */
	private Timestamp authDate;
	/**
	 * 是否被逻辑删除
	 */
	private Boolean isDeleted;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;
	/**
	 * 再次编辑时间
	 */
	private Timestamp gmtReedit;
	
	public CmsContent() {
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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Short getAuthorType() {
		return authorType;
	}

	public void setAuthorType(Short authorType) {
		this.authorType = authorType;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Long bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Boolean getIsStick() {
		return isStick;
	}

	public void setIsStick(Boolean isStick) {
		this.isStick = isStick;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Timestamp getDeadlineStick() {
		return deadlineStick;
	}

	public void setDeadlineStick(Timestamp deadlineStick) {
		this.deadlineStick = deadlineStick;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Integer getSortHot() {
		return sortHot;
	}

	public void setSortHot(Integer sortHot) {
		this.sortHot = sortHot;
	}

	public Timestamp getDeadlineHot() {
		return deadlineHot;
	}

	public void setDeadlineHot(Timestamp deadlineHot) {
		this.deadlineHot = deadlineHot;
	}

	public Boolean getIsPromote() {
		return isPromote;
	}

	public void setIsPromote(Boolean isPromote) {
		this.isPromote = isPromote;
	}

	public Integer getSortPromote() {
		return sortPromote;
	}

	public void setSortPromote(Integer sortPromote) {
		this.sortPromote = sortPromote;
	}

	public Timestamp getDeadlinePromote() {
		return deadlinePromote;
	}

	public void setDeadlinePromote(Timestamp deadlinePromote) {
		this.deadlinePromote = deadlinePromote;
	}

	public Boolean getIsScheduled() {
		return isScheduled;
	}

	public void setIsScheduled(Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public Boolean getIsHide() {
		return isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Boolean getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}

	public Long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	public Timestamp getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Timestamp authDate) {
		this.authDate = authDate;
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

	public Timestamp getGmtReedit() {
		return gmtReedit;
	}

	public void setGmtReedit(Timestamp gmtReedit) {
		this.gmtReedit = gmtReedit;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", parentId:" + parentId + ", path:" + path
				+ ", mid:" + mid + ", categoryId:" + categoryId + ", title:"
				+ title + ", summary:" + summary + ", keyword:" + keyword
				+ ", authorType:" + authorType + ", authorId:" + authorId
				+ ", clicks:" + clicks + ", comments:" + comments + ", likes:"
				+ likes + ", bookmarks:" + bookmarks + ", isStick:" + isStick
				+ ", sort:" + sort + ", deadlineStick:" + deadlineStick
				+ ", isHot:" + isHot + ", sortHot:" + sortHot
				+ ", deadlineHot:" + deadlineHot + ", isPromote:" + isPromote
				+ ", sortPromote:" + sortPromote + ", deadlinePromote:"
				+ deadlinePromote + ", isScheduled:" + isScheduled
				+ ", isHide:" + isHide + ", isPublished:" + isPublished
				+ ", isAuth:" + isAuth + ", authUserId:" + authUserId
				+ ", authDate:" + authDate + ", isDeleted:" + isDeleted
				+ ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate
				+ ", gmtReedit:" + gmtReedit + "}";
	}
	
	

}