package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家申报表实体类<p>
 * <p>Description:作家申报填写信息<p>
 * @author lyc
 * @date 2017年9月22日 上午9:52:20
 */

@SuppressWarnings("serial")
@Alias("Declaration")
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

	public Declaration(Long id) {
		super();
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Short getExperience() {
		return experience;
	}

	public void setExperience(Short experience) {
		this.experience = experience;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getIdtype() {
		return idtype;
	}

	public void setIdtype(Short idtype) {
		this.idtype = idtype;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Short getOnlineProgress() {
		return onlineProgress;
	}

	public void setOnlineProgress(Short onlineProgress) {
		this.onlineProgress = onlineProgress;
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

	public Short getOfflineProgress() {
		return offlineProgress;
	}

	public void setOfflineProgress(Short offlineProgress) {
		this.offlineProgress = offlineProgress;
	}

	public Timestamp getPaperDate() {
		return paperDate;
	}

	public void setPaperDate(Timestamp paperDate) {
		this.paperDate = paperDate;
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

	@Override
	public String toString() {
		return " {id:" + id + ", materialId:" + materialId + ", userId:"
				+ userId + ", realname:" + realname + ", sex:" + sex
				+ ", birthday:" + birthday + ", experience:" + experience
				+ ", orgName:" + orgName + ", position:" + position
				+ ", title:" + title + ", address:" + address + ", postcode:"
				+ postcode + ", handphone:" + handphone + ", email:" + email
				+ ", idtype:" + idtype + ", idcard:" + idcard + ", telephone:"
				+ telephone + ", fax:" + fax + ", orgId:" + orgId
				+ ", onlineProgress:" + onlineProgress + ", authUserId:"
				+ authUserId + ", authDate:" + authDate + ", offlineProgress:"
				+ offlineProgress + ", paperDate:" + paperDate + ", isStaging:"
				+ isStaging + ", isDeleted:" + isDeleted + ", gmtCreate:"
				+ gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
	}

}