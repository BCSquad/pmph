/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import com.bc.pmpheep.annotation.ExcelHeader;
import org.apache.ibatis.type.Alias;

/**
 * <p>
 * Description:小组成员管理
 * <p>
 * 
 * @author lyc
 * @date 2017年10月11日 上午11:12:44
 */
@SuppressWarnings("serial")
@Alias("PmphGroupMemberManagerVO")
public class PmphGroupMemberManagerVO implements Serializable {
	// 主键
	private Long id;
	// 小组id
	private Long groupId;
	// 书籍id
	private Long bookId;
	// 成员id
	private Long userId;
	// 成员类型 1 社内 2 作家 3 机构
	private Short userType;
	// 是否为作家用户
	private Boolean isWriter;
	// 小组内显示名称;
	@ExcelHeader(header = "姓名")
	private String displayName;
	// 账号
	@ExcelHeader(header = "账号")
	private String username;
	// 账号/名称
	private String name;
	// 权限（身份）
	@ExcelHeader(header = "身份")
	private String identity;
	// 职位
	@ExcelHeader(header = "职位")
	private String position;
	// 工作单位id 社内用户为部门id/作家用户为机构id
	private Long workId;
	// 工作单位
	@ExcelHeader(header = "工作单位")
	private String workName;
	// 联系电话
	private String handphone;
	// 邮箱
	private String email;
	//头像
	private String avatar;
	//地址
	private  String address;

	public PmphGroupMemberManagerVO() {
		super();
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getUserType() {
		return userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPosition() {
		return position;
	}

	public Boolean getIsWriter() {
		return isWriter;
	}

	public void setIsWriter(Boolean isWriter) {
		this.isWriter = isWriter;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
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

	@Override
	public String toString() {
		return "PmphGroupMemberManagerVO [id=" + id + ", groupId=" + groupId + ", bookId=" + bookId + ", userId="
				+ userId + ", displayName=" + displayName + ", username=" + username + ", name=" + name + ", identity="
				+ identity + ", position=" + position + ", workId=" + workId + ", workName=" + workName + ", handphone="
				+ handphone + ", email=" + email + "]";
	}

}
