package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PmphRole实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("PmphRole")
public class PmphRole implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String roleName;
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

	public PmphRole(String roleName, boolean isDisabled, String note, Integer sort, Date gmtCreate, Date gmtUpdate) {
		super();
		this.roleName = roleName;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
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
		return "PmphRole [id=" + id + ", roleName=" + roleName + ", isDisabled=" + isDisabled + ", note=" + note
				+ ", sort=" + sort + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

}
