package com.bc.pmpheep.back.po;
// Generated 2017-9-11 1:19:21 by Hibernate Tools 4.3.1

import java.util.Date;
import org.apache.ibatis.type.Alias;

/**
 * PmphUser 实体类
 */
@Alias("PmphUser")
public class PmphUser implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户名
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
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 部门id
	 */
	private long departmentId;
	/**
	 * 手机
	 */
	private String handphone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 显示顺序
	 */
	private int sort;
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

	public PmphUser() {
	}

	public PmphUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public PmphUser(String username, String password, boolean isDisabled, String realname, long departmentId,
			String handphone, String email, String note, int sort, boolean isDeleted, Date gmtCreate, Date gmtUpdate) {
		this.username = username;
		this.password = password;
		this.isDisabled = isDisabled;
		this.realname = realname;
		this.departmentId = departmentId;
		this.handphone = handphone;
		this.email = email;
		this.note = note;
		this.sort = sort;
		this.isDeleted = isDeleted;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIsDisabled() {
		return this.isDisabled;
	}

	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getHandphone() {
		return this.handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtUpdate() {
		return this.gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	@Override
	public String toString() {
		return "PmphUser [id=" + id + ", username=" + username + ", password=" + password + ", isDisabled=" + isDisabled
				+ ", realname=" + realname + ", departmentId=" + departmentId + ", handphone=" + handphone + ", email="
				+ email + ", note=" + note + ", sort=" + sort + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
				+ ", gmtUpdate=" + gmtUpdate + "]";
	}

}
