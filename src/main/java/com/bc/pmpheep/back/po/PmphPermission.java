package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PmphPermission实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("PmphPermission")
public class PmphPermission implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 权限名称
	 */
	private String peermission;
	/**
	 * 是否禁用
	 */
	private boolean idDeleted;
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

	public PmphPermission(String peermission, boolean idDeleted, String note, Integer sort, Date gmtCreate,
			Date gmtUpdate) {
		super();
		this.peermission = peermission;
		this.idDeleted = idDeleted;
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

	public String getPeermission() {
		return peermission;
	}

	public void setPeermission(String peermission) {
		this.peermission = peermission;
	}

	public boolean isIdDeleted() {
		return idDeleted;
	}

	public void setIdDeleted(boolean idDeleted) {
		this.idDeleted = idDeleted;
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
		return "PmphPermission [id=" + id + ", peermission=" + peermission + ", idDeleted=" + idDeleted + ", note="
				+ note + ", sort=" + sort + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

}
