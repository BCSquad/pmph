package com.bc.pmpheep.back.po;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：CMS内容 实体
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
@Alias("CmsContent")
public class CmsContent implements java.io.Serializable {

    // 主键
    private Long      id;
    // 上级id
    private Long      parentId;
    // 根节点路径
    private String    path;
    // 内容id
    private String    mid;
    // 内容类型
    private Long      categoryId;
    // 内容标题
    private String    title;
    // 摘要
    private String    summary;
    // 关键字
    private String    keyword;
    // 作者类型
    private Short     authorType;
    // 作者id
    private Long      authorId;
    // 点击数
    private Long      clicks;
    // 评论数
    private Long      comments;
    // 点赞数
    private Long      likes;
    // 收藏数
    private Long      bookmarks;
    // 是否分类置顶
    private Boolean   isStick;
    //类型 0-全部 1-pc 2-app
    private int apporpc;
    // 分类显示顺序
    private Integer   sort;
    // 置顶到期时间
    private String    deadlineStick;
    // 是否热门
    private Boolean   isHot;
    // 热门显示顺序
    private Integer   sortHot;
    // 热门到期时间
    private String    deadlineHot;
    // 是否推荐
    private Boolean   isPromote;
    // 推荐显示顺序
    private Integer   sortPromote;
    // 推荐到期时间
    private String    deadlinePromote;
    // 是否定时发布
    private Boolean   isScheduled;
    // 是否隐藏
    private Boolean   isHide;
    // 是否已发布
    private Boolean   isPublished;
    // 是否通过审核
    private Boolean   isAuth;
    // 审核状态
    private Short     authStatus;
    // 审核者id
    private Long      authUserId;
    // 审核通过时间
    private String    authDate;
    // 是否被逻辑删除
    private Boolean   isDeleted;
    // 创建时间
    private Timestamp gmtCreate;
    // 修改时间
    private Timestamp gmtUpdate;
    // 再次编辑时间
    private String    gmtReedit;
    // 教材id
    private Long      materialId;
    // 是否暂存
    private Boolean   isStaging;
    // 是否教材报名入口
    private Boolean   isMaterialEntry;
    // 封面id
    private String    cover;
    // 退回理由
    private String    returnReason;
    //作家姓名
    private String authorname;

    // Constructors

    /** default constructor */
    public CmsContent() {
    }

    public CmsContent(Long id, String mid, String gmtReedit) {
        this.id = id;
        this.mid = mid;
        this.gmtReedit = gmtReedit;
    }

    public CmsContent(Long id, String cover) {
        this.id = id;
        this.cover = cover;
    }

    public CmsContent(Long id, Boolean isPublished, Timestamp gmtUpdate) {
        this.id = id;
        this.isPublished = isPublished;
        this.gmtUpdate = gmtUpdate;
    }

    public CmsContent(Long id, Boolean isPublished, Boolean isStaging, Short authStatus,
    Long authUserId, String authDate) {
        this.id = id;
        this.isPublished = isPublished;
        this.isStaging = isStaging;
        this.authStatus = authStatus;
        this.authUserId = authUserId;
        this.authDate = authDate;
    }

    public CmsContent(Long id, Short authStatus, Long authUserId, String authDate,
    Boolean isPublished, Boolean isStaging, Long materialId) {
        this.id = id;
        this.authStatus = authStatus;
        this.authUserId = authUserId;
        this.authDate = authDate;
        this.isPublished = isPublished;
        this.isStaging = isStaging;
        this.materialId = materialId;
    }

    public CmsContent(Long id, Long parentId, String path, String gmtReedit, Long materialId) {
        this.id = id;
        this.parentId = parentId;
        this.path = path;
        this.gmtReedit = gmtReedit;
        this.materialId = materialId;
    }

    public CmsContent(Long parentId, String path, String mid, String title, Short authorType,
    Boolean isPublished, Boolean isStaging, Long authorId, String gmtReedit, Long materialId,
    Long categoryId, Boolean isMaterialEntry, String cover) {
        this.parentId = parentId;
        this.path = path;
        this.mid = mid;
        this.title = title;
        this.authorType = authorType;
        this.isPublished = isPublished;
        this.isStaging = isStaging;
        this.authorId = authorId;
        this.gmtReedit = gmtReedit;
        this.materialId = materialId;
        this.categoryId = categoryId;
        this.isMaterialEntry = isMaterialEntry;
        this.cover = cover;
    }

    public CmsContent(Long parentId, String path, String mid, String title, String summary,
    String keyword, Short authorType, String deadlineStick, String deadlineHot,
    String deadlinePromote, Long authUserId, String authDate, String gmtReedit, Long materialId,
    Long categoryId) {
        this.parentId = parentId;
        this.path = path;
        this.mid = mid;
        this.title = title;
        this.summary = summary;
        this.keyword = keyword;
        this.authorType = authorType;
        this.deadlineStick = deadlineStick;
        this.deadlineHot = deadlineHot;
        this.deadlinePromote = deadlinePromote;
        this.authUserId = authUserId;
        this.authDate = authDate;
        this.gmtReedit = gmtReedit;
        this.materialId = materialId;
        this.categoryId = categoryId;
    }

    /** minimal constructor */
    public CmsContent(Long parentId, String path, String mid, Long categoryId, String title,
    Short authorType, Short authStatus, Long authorId, Long clicks, Long comments, Long likes,
    Long bookmarks, Boolean isStick, Integer sort, Boolean isHot, Integer sortHot,
    Boolean isPromote, Integer sortPromote, Boolean isScheduled, Boolean isHide,
    Boolean isPublished, Boolean isAuth, Boolean isDeleted, Timestamp gmtCreate,
    Timestamp gmtUpdate, Long materialId) {
        this.parentId = parentId;
        this.path = path;
        this.mid = mid;
        this.categoryId = categoryId;
        this.title = title;
        this.authorType = authorType;
        this.authStatus = authStatus;
        this.authorId = authorId;
        this.clicks = clicks;
        this.comments = comments;
        this.likes = likes;
        this.bookmarks = bookmarks;
        this.isStick = isStick;
        this.sort = sort;
        this.isHot = isHot;
        this.sortHot = sortHot;
        this.isPromote = isPromote;
        this.sortPromote = sortPromote;
        this.isScheduled = isScheduled;
        this.isHide = isHide;
        this.isPublished = isPublished;
        this.isAuth = isAuth;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.materialId = materialId;
    }

    /** full constructor */
    public CmsContent(Long parentId, String path, String mid, Long categoryId, String title,
    String summary, String keyword, Short authorType, Short authStatus, Long authorId, Long clicks,
    Long comments, Long likes, Long bookmarks, Boolean isStick, Integer sort, String deadlineStick,
    Boolean isHot, Integer sortHot, String deadlineHot, Boolean isPromote, Integer sortPromote,
    String deadlinePromote, Boolean isScheduled, Boolean isHide, Boolean isPublished,
    Boolean isAuth, Long authUserId, String authDate, Boolean isDeleted, Timestamp gmtCreate,
    Timestamp gmtUpdate, String gmtReedit, Long materialId, Boolean isMaterialEntry, String cover) {
        this.parentId = parentId;
        this.path = path;
        this.mid = mid;
        this.categoryId = categoryId;
        this.title = title;
        this.summary = summary;
        this.keyword = keyword;
        this.authorType = authorType;
        this.authStatus = authStatus;
        this.authorId = authorId;
        this.clicks = clicks;
        this.comments = comments;
        this.likes = likes;
        this.bookmarks = bookmarks;
        this.isStick = isStick;
        this.sort = sort;
        this.deadlineStick = deadlineStick;
        this.isHot = isHot;
        this.sortHot = sortHot;
        this.deadlineHot = deadlineHot;
        this.isPromote = isPromote;
        this.sortPromote = sortPromote;
        this.deadlinePromote = deadlinePromote;
        this.isScheduled = isScheduled;
        this.isHide = isHide;
        this.isPublished = isPublished;
        this.isAuth = isAuth;
        this.authUserId = authUserId;
        this.authDate = authDate;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.gmtReedit = gmtReedit;
        this.materialId = materialId;
        this.cover = cover;
    }

    // Property accessors
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

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Short getAuthorType() {
        return this.authorType;
    }

    public void setAuthorType(Short authorType) {
        this.authorType = authorType;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getClicks() {
        return this.clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getComments() {
        return this.comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getLikes() {
        return this.likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getBookmarks() {
        return this.bookmarks;
    }

    public void setBookmarks(Long bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Boolean getIsStick() {
        return this.isStick;
    }

    public void setIsStick(Boolean isStick) {
        this.isStick = isStick;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDeadlineStick() {
        return this.deadlineStick;
    }

    public void setDeadlineStick(String deadlineStick) {
        this.deadlineStick = deadlineStick;
    }

    public Boolean getIsHot() {
        return this.isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Integer getSortHot() {
        return this.sortHot;
    }

    public void setSortHot(Integer sortHot) {
        this.sortHot = sortHot;
    }

    public String getDeadlineHot() {
        return this.deadlineHot;
    }

    public void setDeadlineHot(String deadlineHot) {
        this.deadlineHot = deadlineHot;
    }

    public Boolean getIsPromote() {
        return this.isPromote;
    }

    public void setIsPromote(Boolean isPromote) {
        this.isPromote = isPromote;
    }

    public Integer getSortPromote() {
        return this.sortPromote;
    }

    public void setSortPromote(Integer sortPromote) {
        this.sortPromote = sortPromote;
    }

    public String getDeadlinePromote() {
        return this.deadlinePromote;
    }

    public void setDeadlinePromote(String deadlinePromote) {
        this.deadlinePromote = deadlinePromote;
    }

    public Boolean getIsScheduled() {
        return this.isScheduled;
    }

    public void setIsScheduled(Boolean isScheduled) {
        this.isScheduled = isScheduled;
    }

    public Boolean getIsHide() {
        return this.isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getIsAuth() {
        return this.isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }

    public Long getAuthUserId() {
        return this.authUserId;
    }

    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
    }

    public String getAuthDate() {
        return this.authDate;
    }

    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return this.gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getGmtReedit() {
        return this.gmtReedit;
    }

    public void setGmtReedit(String gmtReedit) {
        this.gmtReedit = gmtReedit;
    }

    /**
     * @return the authStatus
     */
    public Short getAuthStatus() {
        return authStatus;
    }

    /**
     * @param authStatus the authStatus to set
     */
    public void setAuthStatus(Short authStatus) {
        this.authStatus = authStatus;
    }

    /**
     * @return the materialId
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * @return the isStaging
     */
    public Boolean getIsStaging() {
        return isStaging;
    }

    /**
     * @param isStaging the isStaging to set
     */
    public void setIsStaging(Boolean isStaging) {
        this.isStaging = isStaging;
    }

    /**
     * @return the isMaterialEntry
     */
    public Boolean getIsMaterialEntry() {
        return isMaterialEntry;
    }

    /**
     * @param isMaterialEntry the isMaterialEntry to set
     */
    public void setIsMaterialEntry(Boolean isMaterialEntry) {
        this.isMaterialEntry = isMaterialEntry;
    }

    /**
     * @return the cover
     */
    public String getCover() {
        return cover;
    }

    /**
     * @param cover the cover to set
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * @return the returnReason
     */
    public String getReturnReason() {
        return returnReason;
    }

    /**
     * @param returnReason the returnReason to set
     */
    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
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
        return " {id:" + id + ", parentId:" + parentId + ", path:" + path + ", mid:" + mid
               + ", categoryId:" + categoryId + ", title:" + title + ", summary:" + summary
               + ", keyword:" + keyword + ", authorType:" + authorType + ",authStatus:"
               + authStatus + " authorId:" + authorId + ", clicks:" + clicks + ", comments:"
               + comments + ", likes:" + likes + ", bookmarks:" + bookmarks + ", isStick:"
               + isStick + ", sort:" + sort + ", deadlineStick:" + deadlineStick + ", isHot:"
               + isHot + ", sortHot:" + sortHot + ", deadlineHot:" + deadlineHot + ", isPromote:"
               + isPromote + ", sortPromote:" + sortPromote + ", deadlinePromote:"
               + deadlinePromote + ", isScheduled:" + isScheduled + ", isHide:" + isHide
               + ", isPublished:" + isPublished + ", isAuth:" + isAuth + ", authUserId:"
               + authUserId + ", authDate:" + authDate + ", isDeleted:" + isDeleted
               + ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate + ", gmtReedit:"
               + gmtReedit + ", materialId:" + materialId + ", isMaterialEntry:" + isMaterialEntry
               + ", cover:" + cover + "}";
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public int getApporpc() {
        return apporpc;
    }

    public void setApporpc(int apporpc) {
        this.apporpc = apporpc;
    }
}