package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * OrgUser 实体类
 * 
 * @author Thinkpad
 *
 */
@Alias("OrgUser")
public class OrgUser implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 机构代码
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 是否禁用
	 */
	private boolean isDisabled;
	/**
	 * 对应学校ID
	 */
	private Long orgId;
	/**
	 * 管理员姓名
	 */
	private String realname;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 职务
	 */
	private String position;
	/**
	 * 职称
	 */
	private String title;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 手机
	 */
	private String handphone;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 身份证
	 */
	private String idcard;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String postcode;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 是否逻辑删除
	 */
	private boolean isDeleted;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtUpdate;

	public OrgUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public OrgUser(String username, String password, boolean isDisabled, Long orgId, String realname, Integer sex,
			String position, String title, String fax, String handphone, String telephone, String idcard, String email,
			String address, String postcode, String note, Integer sort, boolean isDeleted, Date gmtCreate,
			Date gmtUpdate) {
		this.username = username;
		this.password = password;
		this.isDisabled = isDisabled;
		this.orgId = orgId;
		this.realname = realname;
		this.sex = sex;
		this.position = position;
		this.title = title;
		this.fax = fax;
		this.handphone = handphone;
		this.telephone = telephone;
		this.idcard = idcard;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.note = note;
		this.sort = sort;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(boolean idDisabled) {
		this.isDisabled = idDisabled;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	@Override
	public String toString() {
		return "OrgUser [id=" + id + ", username=" + username + ", password=" + password + ", idDisabled=" + isDisabled
				+ ", orgId=" + orgId + ", realname=" + realname + ", sex=" + sex + ", position=" + position + ", title="
				+ title + ", fax=" + fax + ", handphone=" + handphone + ", telephone=" + telephone + ", idcard="
				+ idcard + ", email=" + email + ", address=" + address + ", postcode=" + postcode + ", note=" + note
				+ ", sort=" + sort + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate + ", gmtUpdate="
				+ gmtUpdate + "]";
	}

}
