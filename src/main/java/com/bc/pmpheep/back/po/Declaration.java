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

    //主键
	private Long id;
	//教材id
	private Long materialId;
	//作家id
	private Long userId;
	//作家真实姓名
	private String realname;
	//作家性别
	private Integer sex;
	//作家生日
	private Date birthday;
	//作家教龄
	private Integer experience;
	//作家工作单位
	private String orgName;
	//作家职务
	private String position;
	//作家职称
	private String title;
	//作家地址
	private String address;
	//作家邮编
	private String postcode;
	//作家手机
	private String handphone;
	//作家邮箱
	private String email;
	//作家证件类型
	private Short idtype;
	//作家证件号码
	private String idcard;
	//作家电话
	private String telephone;
	//作家传真
	private String fax;
	// 服从调剂
	private Integer isDispensed;
	// 参与本科教学评估认证
	private Integer isUtec;
	// 学历
	private Integer degree;
	// 专业特长
	private String expertise;
	//作家申报单位id
	private Long orgId;
	//审核进度
	private Integer onlineProgress;
	//审核人id
	private Long authUserId;
	//审核通过时间
	private Timestamp authDate;
	//纸质表进度
	private Integer offlineProgress;
	//纸质表收到时间
	private Timestamp paperDate;
	// 退回原因
	private String returnCause;
	//是否暂存
	private boolean isStaging;
	//是否逻辑删除
	private boolean isDeleted;
	//创建时间
	private Timestamp gmtCreate;
	//修改时间
	private Timestamp gmtUpdate;

	// 构造器

	/** default constructor */
	public Declaration() {
	}

    

	public Declaration(Long id) {
		super();
		this.id = id;
	}

	public Declaration(Long materialId, Long userId) {
		super();
		this.materialId = materialId;
		this.userId = userId;
	}

	public Declaration(Long materialId, Long userId, String realname,
			Integer sex, Date birthday, Integer experience, String orgName,
			String position, String title, String address, String postcode,
			String handphone, String email, Short idtype, String idcard,
			String telephone, String fax, Long orgId, Integer onlineProgress,
			Long authUserId, Timestamp authDate, Integer offlineProgress,
			Timestamp paperDate, boolean isStaging, boolean isDeleted) {
		super();
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
	}



	/** full constructor */
	public Declaration(Long materialId, Long userId, String realname,
			Integer sex, Date birthday, Integer experience, String orgName,
			String position, String title, String address, String postcode,
			String handphone, String email, Short idtype, String idcard,
			String telephone, String fax, Long orgId, Integer onlineProgress,
			Long authUserId, Timestamp authDate, Integer offlineProgress,
			Timestamp paperDate, boolean isStaging, boolean isDeleted,
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
	
	public Declaration(Long materialId, Long userId, String realname,
			Integer sex, Date birthday, Integer experience, String orgName,
			String position, String title, String address, String postcode,
			String handphone, String email, Short idtype, String idcard,
			String telephone, String fax, Integer isDispensed, Long orgId, 
			Integer onlineProgress, Long authUserId, Timestamp authDate, 
			Integer offlineProgress, Timestamp paperDate, boolean isStaging, 
			boolean isDeleted) {
		super();
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
		this.isDispensed = isDispensed;
		this.orgId = orgId;
		this.onlineProgress = onlineProgress;
		this.authUserId = authUserId;
		this.authDate = authDate;
		this.offlineProgress = offlineProgress;
		this.paperDate = paperDate;
		this.isStaging = isStaging;
		this.isDeleted = isDeleted;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
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

	public Integer getOnlineProgress() {
		return onlineProgress;
	}

	public void setOnlineProgress(Integer onlineProgress) {
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

	public Integer getOfflineProgress() {
		return offlineProgress;
	}

	public void setOfflineProgress(Integer offlineProgress) {
		this.offlineProgress = offlineProgress;
	}

	public Timestamp getPaperDate() {
		return paperDate;
	}

	public void setPaperDate(Timestamp paperDate) {
		this.paperDate = paperDate;
	}

	public boolean getIsStaging() {
		return isStaging;
	}

	public void setIsStaging(boolean isStaging) {
		this.isStaging = isStaging;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
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

	public Integer getIsDispensed() {
		return isDispensed;
	}

	public void setIsDispensed(Integer isDispensed) {
		this.isDispensed = isDispensed;
	}
	
	public Integer getIsUtec() {
		return isUtec;
	}
	
	public void setIsUtec(Integer isUtec) {
		this.isUtec = isUtec;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getReturnCause() {
		return returnCause;
	}

	public void setReturnCause(String returnCause) {
		this.returnCause = returnCause;
	}

	@Override
	public String toString() {
		return "Declaration [id=" + id + ", materialId=" + materialId
				+ ", userId=" + userId + ", realname=" + realname + ", sex="
				+ sex + ", birthday=" + birthday + ", experience=" + experience
				+ ", orgName=" + orgName + ", position=" + position
				+ ", title=" + title + ", address=" + address + ", postcode="
				+ postcode + ", handphone=" + handphone + ", email=" + email
				+ ", idtype=" + idtype + ", idcard=" + idcard + ", telephone="
				+ telephone + ", fax=" + fax + ", isDispensed=" + isDispensed
				+ ", isUtec=" + isUtec + ", degree=" + degree + ", expertise="
				+ expertise + ", orgId=" + orgId + ", onlineProgress="
				+ onlineProgress + ", authUserId=" + authUserId + ", authDate="
				+ authDate + ", offlineProgress=" + offlineProgress
				+ ", paperDate=" + paperDate + ", returnCause=" + returnCause
				+ ", isStaging=" + isStaging + ", isDeleted=" + isDeleted
				+ ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}
}