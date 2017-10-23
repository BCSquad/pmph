package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 上午9:33:25
 * 
 **/
@SuppressWarnings("serial")
@Alias("OrgVO")
public class OrgVO implements Serializable {
	// 主键
	private String id;
	// 机构名称
	private String orgName;
	// 机构类型id
	private String orgTypeId;
	// 机构类型名称
	private String orgTypeName;
	// 上级机构id
	private String parentId;
	// 区域id
	private String areaId;
	// 所属区域名称
	private String areaName;
	// 联系人
	private String contactPerson;
	// 联系电话
	private String contactPhone;
	// 备注
	private String note;
	// 显示顺序
	private Integer sort;
	// 条件分页总条数
	private Integer count;
	// 创建时间
	private Timestamp gmtCreate;

	public OrgVO() {
		super();
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

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "OrgVO [id=" + id + ", orgName=" + orgName + ", orgTypeId=" + orgTypeId + ", orgTypeName=" + orgTypeName
				+ ", parentId=" + parentId + ", areaId=" + areaId + ", areaName=" + areaName + ", countactPerson="
				+ contactPerson + ", countactPhone=" + contactPhone + ", note=" + note + ", sort=" + sort + ", count="
				+ count + ", gmtCreate=" + gmtCreate + "]";
	}

}
