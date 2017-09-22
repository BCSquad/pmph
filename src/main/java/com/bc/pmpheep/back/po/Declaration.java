package com.test;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Declaration entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "declaration", catalog = "pmph_imesp_db")
public class Declaration implements java.io.Serializable {

	// Fields

	private Long id;
	private Long materialId;
	private Long userId;
	private String realname;
	private Short sex;
	private Date birthday;
	private Short experience;
	private String orgName;
	private String position;
	private String title;
	private String address;
	private String postcode;
	private String handphone;
	private String email;
	private Short idtype;
	private String idcard;
	private String telephone;
	private String fax;
	private Long orgId;
	private Short onlineProgress;
	private Long authUserId;
	private Timestamp authDate;
	private Short offlineProgress;
	private Timestamp paperDate;
	private Boolean isStaging;
	private Boolean isDeleted;
	private Timestamp gmtCreate;
	private Timestamp gmtUpdate;

	// Constructors

	/** default constructor */
	public Declaration() {
	}

	/** minimal constructor */
	public Declaration(Long materialId, Long userId, Short onlineProgress,
			Short offlineProgress, Boolean isStaging, Boolean isDeleted,
			Timestamp gmtCreate, Timestamp gmtUpdate) {
		this.materialId = materialId;
		this.userId = userId;
		this.onlineProgress = onlineProgress;
		this.offlineProgress = offlineProgress;
		this.isStaging = isStaging;
		this.isDeleted = isDeleted;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	/** full constructor */
	public Declaration(Long materialId, Long userId, String realname,
			Short sex, Date birthday, Short experience, String orgName,
			String position, String title, String address, String postcode,
			String handphone, String email, Short idtype, String idcard,
			String telephone, String fax, Long orgId, Short onlineProgress,
			Long authUserId, Timestamp authDate, Short offlineProgress,
			Timestamp paperDate, Boolean isStaging, Boolean isDeleted,
			Timestamp gmtCreate, Timestamp gmtUpdate) {
		this.materialId = materialId;
		this.userId = userId;
		this.realname = realname;
		this.sex = sex;
		this.birthday = birthday;
		this.experience = experience;
		this.orgName = orgName;
		this.position = position;
		this.title = title;
		this.address = address;
		this.postcode = postcode;
		this.handphone = handphone;
		this.email = email;
		this.idtype = idtype;
		this.idcard = idcard;
		this.telephone = telephone;
		this.fax = fax;
		this.orgId = orgId;
		this.onlineProgress = onlineProgress;
		this.authUserId = authUserId;
		this.authDate = authDate;
		this.offlineProgress = offlineProgress;
		this.paperDate = paperDate;
		this.isStaging = isStaging;
		this.isDeleted = isDeleted;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "material_id", nullable = false)
	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "realname", length = 20)
	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(name = "sex")
	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "experience")
	public Short getExperience() {
		return this.experience;
	}

	public void setExperience(Short experience) {
		this.experience = experience;
	}

	@Column(name = "org_name", length = 60)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "position", length = 36)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "title", length = 30)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "postcode", length = 20)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "handphone", length = 25)
	public String getHandphone() {
		return this.handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	@Column(name = "email", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "idtype")
	public Short getIdtype() {
		return this.idtype;
	}

	public void setIdtype(Short idtype) {
		this.idtype = idtype;
	}

	@Column(name = "idcard", length = 20)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "telephone", length = 30)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "fax", length = 50)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "org_id")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "online_progress", nullable = false)
	public Short getOnlineProgress() {
		return this.onlineProgress;
	}

	public void setOnlineProgress(Short onlineProgress) {
		this.onlineProgress = onlineProgress;
	}

	@Column(name = "auth_user_id")
	public Long getAuthUserId() {
		return this.authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	@Column(name = "auth_date", length = 19)
	public Timestamp getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(Timestamp authDate) {
		this.authDate = authDate;
	}

	@Column(name = "offline_progress", nullable = false)
	public Short getOfflineProgress() {
		return this.offlineProgress;
	}

	public void setOfflineProgress(Short offlineProgress) {
		this.offlineProgress = offlineProgress;
	}

	@Column(name = "paper_date", length = 19)
	public Timestamp getPaperDate() {
		return this.paperDate;
	}

	public void setPaperDate(Timestamp paperDate) {
		this.paperDate = paperDate;
	}

	@Column(name = "is_staging", nullable = false)
	public Boolean getIsStaging() {
		return this.isStaging;
	}

	public void setIsStaging(Boolean isStaging) {
		this.isStaging = isStaging;
	}

	@Column(name = "is_deleted", nullable = false)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "gmt_create", nullable = false, length = 19)
	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Column(name = "gmt_update", nullable = false, length = 19)
	public Timestamp getGmtUpdate() {
		return this.gmtUpdate;
	}

	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

}