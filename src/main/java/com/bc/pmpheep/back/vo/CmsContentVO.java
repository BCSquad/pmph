package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.util.DateUtil;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("CmsContentVO")
public class CmsContentVO implements Serializable {
    // 主键
    private Long      id;
    // 内容id
    private String    mid;
    // 内容类型
    private Long      categoryId;
    // 类别名称
    private String    categoryName;
    // 内容标题
    @ExcelHeader(header = " 文章标题")
    private String    title;
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
    // 是否热门
    private Boolean   isHot;
    // 是否推荐
    private Boolean   isPromote;
    // 是否通过审核
    private Boolean   isAuth;
    // 审核者id
    private Long      authUserId;
    @ExcelHeader(header = "审核人")
    private String      authUserName;
    // 审核通过时间
    private Timestamp authDate;
    @ExcelHeader(header = "审核时间")
    private String authDateS;
    // 发布开始时间
    private String    startAuDate;
    // 发布结束时间
    private String    endAuDate;
    // 审核状态
    private Short     authStatus;
    // 审核状态
    @ExcelHeader(header = "审核状态")
    private String     authStatusName;
    // 条件分页总条数分页查询
    private Integer   count;
    // 页面查询条件（状态）
    private Integer   status;
    // 作家姓名
    @ExcelHeader(header = "评论人")
    private String    username;
    // 是否为管理员
    private Boolean   isAdmin;
    // 是否已发布
    private Boolean   isPublished;
    // 是否教材报名入口
    private Boolean   isMaterialEntry;
    // 创建时间
    private Timestamp gmtCreate;
    // 创建时间
    @ExcelHeader(header = "评论时间")
    private String gmtCreateS;
    // 创建开始时间
    private String    startCreateDate;
    // 创建结束时间
    private String    endCreateDate;
    // 真实姓名
    private String    realname;
    // 教材id
    private Long      materialId;
    private String      materialName;
    // 平台类型
    private Integer   platform;
    // 原作家姓名
    private String authorname;

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
     * @return the mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid the mid to set
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the authorType
     */
    public Short getAuthorType() {
        return authorType;
    }

    /**
     * @param authorType the authorType to set
     */
    public void setAuthorType(Short authorType) {
        this.authorType = authorType;
    }

    /**
     * @return the authorId
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId the authorId to set
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * @return the clicks
     */
    public Long getClicks() {
        return clicks;
    }

    /**
     * @param clicks the clicks to set
     */
    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    /**
     * @return the comments
     */
    public Long getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(Long comments) {
        this.comments = comments;
    }

    /**
     * @return the likes
     */
    public Long getLikes() {
        return likes;
    }

    /**
     * @param likes the likes to set
     */
    public void setLikes(Long likes) {
        this.likes = likes;
    }

    /**
     * @return the bookmarks
     */
    public Long getBookmarks() {
        return bookmarks;
    }

    /**
     * @param bookmarks the bookmarks to set
     */
    public void setBookmarks(Long bookmarks) {
        this.bookmarks = bookmarks;
    }

    /**
     * @return the isStick
     */
    public Boolean getIsStick() {
        return isStick;
    }

    /**
     * @param isStick the isStick to set
     */
    public void setIsStick(Boolean isStick) {
        this.isStick = isStick;
    }

    /**
     * @return the isHot
     */
    public Boolean getIsHot() {
        return isHot;
    }

    /**
     * @param isHot the isHot to set
     */
    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    /**
     * @return the isPromote
     */
    public Boolean getIsPromote() {
        return isPromote;
    }

    /**
     * @param isPromote the isPromote to set
     */
    public void setIsPromote(Boolean isPromote) {
        this.isPromote = isPromote;
    }

    /**
     * @return the isAuth
     */
    public Boolean getIsAuth() {
        return isAuth;
    }

    /**
     * @param isAuth the isAuth to set
     */
    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }

    /**
     * @return the authDate
     */
    public Timestamp getAuthDate() {
        return authDate;
    }

    /**
     * @param authDate the authDate to set
     */
    public void setAuthDate(Timestamp authDate) {
        this.authDate = authDate;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
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

    /**
     * @return the isAdmin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the authUserId
     */
    public Long getAuthUserId() {
        return authUserId;
    }

    /**
     * @param authUserId the authUserId to set
     */
    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
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
     * @return the isPublished
     */
    public Boolean getIsPublished() {
        return isPublished;
    }

    /**
     * @param isPublished the isPublished to set
     */
    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
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
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
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
     * @return the platform
     */
    public Integer getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    /**
     * @return the startAuDate
     */
    public String getStartAuDate() {
        return startAuDate;
    }

    /**
     * @param startAuDate the startAuDate to set
     */
    public void setStartAuDate(String startAuDate) {
        this.startAuDate = startAuDate;
    }

    /**
     * @return the endAuDate
     */
    public String getEndAuDate() {
        return endAuDate;
    }

    /**
     * @param endAuDate the endAuDate to set
     */
    public void setEndAuDate(String endAuDate) {
        this.endAuDate = endAuDate;
    }

    /**
     * @return the startCreateDate
     */
    public String getStartCreateDate() {
        return startCreateDate;
    }

    /**
     * @param startCreateDate the startCreateDate to set
     */
    public void setStartCreateDate(String startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    /**
     * @return the endCreateDate
     */
    public String getEndCreateDate() {
        return endCreateDate;
    }

    /**
     * @param endCreateDate the endCreateDate to set
     */
    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
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

    public String getAuthStatusName() {
        return authStatusName;
    }

    public void setAuthStatusName(String authStatusName) {
        /*String[] authArray = new String[]{"待审核","未通过","已通过"};
        this.authStatusName = authArray[this.authStatus];*/
        this.authStatusName = authStatusName;
    }




    public String getAuthDateS() {
        return authDateS;
    }

    public void setAuthDateS(String authDateS) {
        //this.authDateS = DateUtil.formatTimeStamp("yyyy-MM-dd",this.authDate);
        this.authDateS = authDateS;
    }

    public String getAuthUserName() {
        return authUserName;
    }

    public void setAuthUserName(String authUserName) {
        this.authUserName = authUserName;
    }

    public String getGmtCreateS() {
        return gmtCreateS;
    }

    public void setGmtCreateS(String gmtCreateS) {
       // this.gmtCreateS = DateUtil.formatTimeStamp("yyyy-MM-dd",this.gmtCreate);
        this.gmtCreateS = gmtCreateS;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
