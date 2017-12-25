package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：选题申报主表实体
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月19日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("Topic")
public class Topic implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 作家id
	 */
	private Long userId;
	/**
	 * 书名
	 */
	private String bookname;
	/**
	 * 读者对象 0=医务工作者/1=医学院校师生/2=大众
	 */
	private Integer reader;
	/**
	 * 预计交稿时间
	 */
	private Timestamp deadline;
	/**
	 * 选题来源 0=社策划/1=编辑策划/2=专家策划/3=离退休编审策划/4=上级交办/5=作者投稿
	 */
	private Integer source;
	/**
	 * 估计字数（千字）
	 */
	private Integer wordNumber;
	/**
	 * 预估图数
	 */
	private Integer pictureNumber;
	/**
	 * 学科及专业
	 */
	private String subject;
	/**
	 * 级别 0=低/1=中/2=高
	 */
	private Integer rank;
	/**
	 * 图书类别 0=专著/1=基础理论/2=论文集/3=科普/4=应用技术/5=工具书/6=其他
	 */
	private Integer type;
	/**
	 * 银行账户
	 */
	private Long bankAccountId;
	/**
	 * 作者购书
	 */
	private Integer purchase;
	/**
	 * 作者赞助
	 */
	private Integer sponsorship;
	/**
	 * 是否为翻译书稿
	 */
	private Boolean isTranslation;
	/**
	 * 译稿原书名
	 */
	private String originalBookname;
	/**
	 * 原著作者
	 */
	private String originalAuthor;
	/**
	 * 国籍
	 */
	private String nation;
	/**
	 * 出版年代及版次
	 */
	private String edition;
	/**
	 * 审核进度
	 */
	private Integer authProgress;
	/**
	 * 退回原因/审核意见
	 */
	private String authFeedback;
	/**
	 * 审核时间
	 */
	private Timestamp authDate;
	/**
	 * 是否有运维人员受理
	 */
	private Boolean isOptsHandling;
	/**
	 * 运维人员角色id
	 */
	private Long optsRoleId;
	/**
	 * 是否被主任退回
	 */
	private Boolean isRejectedByDirector;
	/**
	 * 主任退回原因
	 */
	private String reasonDirector;
	/**
	 * 是否由主任受理
	 */
	private Boolean isDirectorHandling;
	/**
	 * 所在部门id
	 */
	private Long departmentId;
	/**
	 * 是否被编辑退回
	 */
	private Boolean isRejectedByEditor;
	/**
	 * 编辑退回原因
	 */
	private String reasonEditor;
	/**
	 * 是否由编辑受理
	 */
	private Boolean isEditorHandling;
	/**
	 * 编辑id（社内用户）
	 */
	private Long editorId;
	/**
	 * 编辑是否接受办理
	 */
	private Boolean isAccepted;
	/**
	 * 是否暂存
	 */
	private Boolean isStaging;
	/**
	 * 是否逻辑删除
	 */
	private Boolean isDeleted;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;
	/**
	 * 选题号
	 */
	private String tn;
	/**
	 * 本版号
	 */
	private String vn;
	/**
	 * 提交日期(前台到后台的日期)
	 */
	private Timestamp submitTime;

	public Topic() {
		super();
	}

	public Topic(Long userId, String bookname, Integer reader, Timestamp deadline, Integer source, Integer wordNumber,
			Integer pictureNumber, String subject, Integer rank, Integer type, Long bankAccountId, Integer purchase,
			Integer sponsorship, Boolean isTranslation, String originalBookname, String originalAuthor, String nation,
			String edition, Integer authProgress, String authFeedback, Timestamp authDate, Boolean isOptsHandling,
			Long optsRoleId, Boolean isRejectedByDirector, String reasonDirector, Boolean isDirectorHandling,
			Long departmentId, Boolean isRejectedByEditor, String reasonEditor, Boolean isEditorHandling, Long editorId,
			Boolean isAccepted, Boolean isStaging, Boolean isDeleted, String note, Timestamp gmtCreate,
			Timestamp gmtUpdate, String tn, String vn, Timestamp submitTime) {
		super();
		this.userId = userId;
		this.bookname = bookname;
		this.reader = reader;
		this.deadline = deadline;
		this.source = source;
		this.wordNumber = wordNumber;
		this.pictureNumber = pictureNumber;
		this.subject = subject;
		this.rank = rank;
		this.type = type;
		this.bankAccountId = bankAccountId;
		this.purchase = purchase;
		this.sponsorship = sponsorship;
		this.isTranslation = isTranslation;
		this.originalBookname = originalBookname;
		this.originalAuthor = originalAuthor;
		this.nation = nation;
		this.edition = edition;
		this.authProgress = authProgress;
		this.authFeedback = authFeedback;
		this.authDate = authDate;
		this.isOptsHandling = isOptsHandling;
		this.optsRoleId = optsRoleId;
		this.isRejectedByDirector = isRejectedByDirector;
		this.reasonDirector = reasonDirector;
		this.isDirectorHandling = isDirectorHandling;
		this.departmentId = departmentId;
		this.isRejectedByEditor = isRejectedByEditor;
		this.reasonEditor = reasonEditor;
		this.isEditorHandling = isEditorHandling;
		this.editorId = editorId;
		this.isAccepted = isAccepted;
		this.isStaging = isStaging;
		this.isDeleted = isDeleted;
		this.note = note;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.tn = tn;
		this.vn = vn;
		this.submitTime = submitTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Integer getReader() {
		return reader;
	}

	public void setReader(Integer reader) {
		this.reader = reader;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getWordNumber() {
		return wordNumber;
	}

	public void setWordNumber(Integer wordNumber) {
		this.wordNumber = wordNumber;
	}

	public Integer getPictureNumber() {
		return pictureNumber;
	}

	public void setPictureNumber(Integer pictureNumber) {
		this.pictureNumber = pictureNumber;
	}

	public String getAuthFeedback() {
		return authFeedback;
	}

	public void setAuthFeedback(String authFeedback) {
		this.authFeedback = authFeedback;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Integer getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(Integer sponsorship) {
		this.sponsorship = sponsorship;
	}

	public Boolean getIsTranslation() {
		return isTranslation;
	}

	public void setIsTranslation(Boolean isTranslation) {
		this.isTranslation = isTranslation;
	}

	public String getOriginalBookname() {
		return originalBookname;
	}

	public void setOriginalBookname(String originalBookname) {
		this.originalBookname = originalBookname;
	}

	public String getOriginalAuthor() {
		return originalAuthor;
	}

	public void setOriginalAuthor(String originalAuthor) {
		this.originalAuthor = originalAuthor;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public Integer getAuthProgress() {
		return authProgress;
	}

	public void setAuthProgress(Integer authProgress) {
		this.authProgress = authProgress;
	}

	public Timestamp getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Timestamp authDate) {
		this.authDate = authDate;
	}

	public Boolean getIsOptsHandling() {
		return isOptsHandling;
	}

	public void setIsOptsHandling(Boolean isOptsHandling) {
		this.isOptsHandling = isOptsHandling;
	}

	public Long getOptsRoleId() {
		return optsRoleId;
	}

	public void setOptsRoleId(Long optsRoleId) {
		this.optsRoleId = optsRoleId;
	}

	public Boolean getIsRejectedByDirector() {
		return isRejectedByDirector;
	}

	public void setIsRejectedByDirector(Boolean isRejectedByDirector) {
		this.isRejectedByDirector = isRejectedByDirector;
	}

	public String getReasonDirector() {
		return reasonDirector;
	}

	public void setReasonDirector(String reasonDirector) {
		this.reasonDirector = reasonDirector;
	}

	public Boolean getIsDirectorHandling() {
		return isDirectorHandling;
	}

	public void setIsDirectorHandling(Boolean isDirectorHandling) {
		this.isDirectorHandling = isDirectorHandling;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Boolean getIsRejectedByEditor() {
		return isRejectedByEditor;
	}

	public void setIsRejectedByEditor(Boolean isRejectedByEditor) {
		this.isRejectedByEditor = isRejectedByEditor;
	}

	public String getReasonEditor() {
		return reasonEditor;
	}

	public void setReasonEditor(String reasonEditor) {
		this.reasonEditor = reasonEditor;
	}

	public Boolean getIsEditorHandling() {
		return isEditorHandling;
	}

	public void setIsEditorHandling(Boolean isEditorHandling) {
		this.isEditorHandling = isEditorHandling;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Boolean getIsStaging() {
		return isStaging;
	}

	public void setIsStaging(Boolean isStaging) {
		this.isStaging = isStaging;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

}
