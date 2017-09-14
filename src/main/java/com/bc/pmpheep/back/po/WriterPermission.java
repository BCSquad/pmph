package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * WriterPermission实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("WriterPermission")
public class WriterPermission implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 权限名称
	 */
	private String permissionName;
	/**
	 * 是否禁用
	 */
	private boolean isDisabled;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtUpdate;
	
	

	public WriterPermission() {
	}

	public WriterPermission(String permissionName, boolean isDisabled, String note, Integer sort, Date gmtCreate,
			Date gmtUpdate) {
		this.permissionName = permissionName;
		this.isDisabled = isDisabled;
		this.note = note;
		this.sort = sort;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public boolean isIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
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
		return "WriterPermission [id=" + id + ", permissionName=" + permissionName + ", idDisabled=" + isDisabled
				+ ", note=" + note + ", sort=" + sort + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

}
