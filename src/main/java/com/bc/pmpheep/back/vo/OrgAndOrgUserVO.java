package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.annotation.ExcelHeader;

/**
 * 
 * @author M 2017-11-16
 */
@SuppressWarnings("serial")
@Alias("OrgAndOrgUserVO")
public class OrgAndOrgUserVO implements Serializable {
	// 主键
	private Long id;
	// 机构名称
	@ExcelHeader(header = "机构名称")
	private String orgName;
	// 机构代码
	@ExcelHeader(header = "机构账号")
	private String username;
	// 管理员姓名
	@ExcelHeader(header = "管理员姓名")
	private String realname;
	// 机构类型id
	private Long orgTypeId;
	// 机构类型名称
	//@ExcelHeader(header = "机构类型")
	private String orgTypeName;
	// 上级机构id
	private String parentId;
	// 区域id
	private Long areaId;
	// 所属区域名称
	private String areaName;
	// 联系人
	private String contactPerson;
	// 联系电话
	private String contactPhone;
	// 审核进度
	private Integer progress;
	// 手机
	@ExcelHeader(header = "手机号")
	private String handphone;
	// 邮箱
	@ExcelHeader(header = "邮箱")
	private String email;
	// 职务
	@ExcelHeader(header = "职务")
	private String position;
	// 职称
	@ExcelHeader(header = "职称")
	private String title;
	// 地址
	@ExcelHeader(header = "邮寄地址")
	private String address;
	// 邮编
	@ExcelHeader(header = "邮编")
	private String postcode;
	// 委托书
	private String proxy;
	// 备注
	/*@ExcelHeader(header = "备注")*/
	private String note;
	// 显示顺序
	private Integer sort;
	// 条件分页总条数
	private Integer count;
	// 创建时间
	private Timestamp gmtCreate;
	// 是否禁用
	private Boolean isDisabled;
	// 对应学校机id
	private Long orgId;
	// 姓名或账号
	private String name;
	// 医院或者学校
	private Integer isHospital;
	// //学校
	// private String school;
	// //医院
	// private String hospital;

	// public String getHospital() {
	// return hospital;
	// }
	// public void setHospital(String hospital) {
	// this.hospital = hospital;
	// }
	// public String getSchool() {
	// return school;
	// }
	// public void setSchool(String school) {
	// this.school = school;
	// }

	public Integer getIsHospital() {
		return isHospital;
	}

	public void setIsHospital(Integer isHospital) {
		this.isHospital = isHospital;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getOrgTypeId() {
		return orgTypeId;
	}

	public void setOrgTypeId(Long orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
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

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public OrgAndOrgUserVO() {
		super();
	}

	public OrgAndOrgUserVO(Long id, String orgName, String username, String realname, Long orgTypeId,
			String orgTypeName, String parentId, Long areaId, String areaName, String contactPerson,
			String contactPhone, Integer progress, String handphone, String email, String position, String title,
			String address, String postcode, String proxy, String note, Integer sort, Integer count,
			Timestamp gmtCreate, Boolean isDisabled, Long orgId, String name) {
		this.id = id;
		this.orgName = orgName;
		this.username = username;
		this.realname = realname;
		this.orgTypeId = orgTypeId;
		this.orgTypeName = orgTypeName;
		this.parentId = parentId;
		this.areaId = areaId;
		this.areaName = areaName;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.progress = progress;
		this.handphone = handphone;
		this.email = email;
		this.position = position;
		this.title = title;
		this.address = address;
		this.postcode = postcode;
		this.proxy = proxy;
		this.note = note;
		this.sort = sort;
		this.count = count;
		this.gmtCreate = gmtCreate;
		this.isDisabled = isDisabled;
		this.orgId = orgId;
		this.name = name;
	}

	@Override
	public String toString() {
		return "OrgAndOrgUserVO [id=" + id + ", orgName=" + orgName + ", username=" + username + ", realname="
				+ realname + ", orgTypeId=" + orgTypeId + ", orgTypeName=" + orgTypeName + ", parentId=" + parentId
				+ ", areaId=" + areaId + ", areaName=" + areaName + ", contactPerson=" + contactPerson
				+ ", contactPhone=" + contactPhone + ", progress=" + progress + ", handphone=" + handphone + ", email="
				+ email + ", position=" + position + ", title=" + title + ", address=" + address + ", postcode="
				+ postcode + ", proxy=" + proxy + ", note=" + note + ", sort=" + sort + ", count=" + count
				+ ", gmtCreate=" + gmtCreate + ", isDisabled=" + isDisabled + ", orgId=" + orgId + ", name=" + name
				+ "]";
	}

}
