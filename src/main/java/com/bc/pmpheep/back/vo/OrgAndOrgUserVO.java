package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * @author M
 *	2017-11-16
 */
@SuppressWarnings("serial")
@Alias("OrgAndOrgUserVO")
public class OrgAndOrgUserVO implements Serializable{
	 // 主键
    private String    id;
    // 机构名称
    private String    orgName;
    // 机构代码
    private String    username;
    // 管理员姓名
    private String    realname;
    // 机构类型id
    private String    orgTypeId;
    // 机构类型名称
    private String    orgTypeName;
    // 上级机构id
    private String    parentId;
    // 区域id
    private String    areaId;
    // 所属区域名称
    private String    areaName;
    // 联系人 
    private String    contactPerson;
    // 联系电话
    private String    contactPhone;
    // 审核进度
    private Integer   progress;
    //手机
    private String    handphone;
    //邮箱
    private String    email;
    //职务
    private String    position;
    //职称
    private String    title;
     //地址
    private String    address;
     // 邮编
    private String    postcode;
    //委托书
    private String    proxy;
    // 备注
    private String    note;
    // 显示顺序
    private Integer   sort;
    // 条件分页总条数
    private Integer   count;
    // 创建时间
    private Timestamp gmtCreate;
    //是否禁用
    private boolean disabled;
    // 对应学校机id
    private Long    orgId;
    //姓名或账号
    private String     name;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getOrgTypeId() {
		return orgTypeId;
	}
	public void setOrgTypeId(String orgTypeId) {
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
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
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
	public boolean isdisabled() {
		return disabled;
	}
	public void setdisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Override
	public String toString() {
		return "OrgAndOrgUserVO [id=" + id + ", orgName=" + orgName + ", username=" + username + ", realname="
				+ realname + ", orgTypeId=" + orgTypeId + ", orgTypeName=" + orgTypeName + ", parentId=" + parentId
				+ ", areaId=" + areaId + ", areaName=" + areaName + ", contactPerson=" + contactPerson
				+ ", contactPhone=" + contactPhone + ", progress=" + progress + ", handphone=" + handphone + ", email="
				+ email + ", position=" + position + ", title=" + title + ", address=" + address + ", postcode="
				+ postcode + ", proxy=" + proxy + ", note=" + note + ", sort=" + sort + ", count=" + count
				+ ", gmtCreate=" + gmtCreate + ", disabled=" + disabled + ", orgId=" + orgId + ", name=" + name
				+ "]";
	}
	public OrgAndOrgUserVO(String id, String orgName, String username, String realname, String orgTypeId,
			String orgTypeName, String parentId, String areaId, String areaName, String contactPerson,
			String contactPhone, Integer progress, String handphone, String email, String position, String title,
			String address, String postcode, String proxy, String note, Integer sort, Integer count,
			Timestamp gmtCreate, boolean disabled, Long orgId, String name) {
		super();
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
		this.disabled = disabled;
		this.orgId = orgId;
		this.name = name;
	}
	public OrgAndOrgUserVO() {
		super();
	}
    
}
