package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * Org (机构信息表)实体类  -- org
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("Org")
public class Org implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 上级机构id
	 */
	private Long parentId;
	/**
	 * 机构名称
	 */
	private String orgName;
	/**
	 * 机构类型ID
	 */
	private Long orgTypeId;
	/**
	 * 所在区域ID
	 */
	private Long areaId;
	/**
	 * 联系人
	 */
	private String countactPerson;
	/**
	 * 联系电话
	 */
	private String countactPhone;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 是否被逻辑删除
	 */
	private boolean isDeleted;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;
	
	public Org(){
		
	}
	public Org(Long id){
		this.id=id;
	}
	/**
	 * 
	 * @param parentId  上级机构id
	 * @param orgName   机构名称
	 * @param orgTypeId  机构类型id
	 * @param areaId    所在区域id
	 * @param countactPerson  联系人
	 * @param countactPhone  联系电话
	 * @param note   备注
	 * @param sort   显示顺序  
	 * @param isDeleted  是否被逻辑删除
	 * @param gmtCreate
	 * @param gmtUpdate
	 */
	public Org(Long parentId, String orgName, Long orgTypeId, Long areaId, String countactPerson, String countactPhone,
			String note, Integer sort, boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate) {
		this.parentId = parentId;
		this.orgName = orgName;
		this.orgTypeId = orgTypeId;
		this.areaId = areaId;
		this.countactPerson = countactPerson;
		this.countactPhone = countactPhone;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgTypeId() {
		return orgTypeId;
	}

	public void setOrgTypeId(Long orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getCountactPerson() {
		return countactPerson;
	}

	public void setCountactPerson(String countactPerson) {
		this.countactPerson = countactPerson;
	}

	public String getCountactPhone() {
		return countactPhone;
	}

	public void setCountactPhone(String countactPhone) {
		this.countactPhone = countactPhone;
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
		return "{id:" + id + ", parentId:" + parentId + ", orgName:" + orgName + ", orgTypeId:" + orgTypeId
				+ ", areaId:" + areaId + ", countactPerson:" + countactPerson + ", countactPhone:" + countactPhone
				+ ", note:" + note + ", sort:" + sort + ", isDeleted:" + isDeleted + ", gmtCreate:" + gmtCreate
				+ ", gmtUpdate:" + gmtUpdate + "}";
	}
	

}
