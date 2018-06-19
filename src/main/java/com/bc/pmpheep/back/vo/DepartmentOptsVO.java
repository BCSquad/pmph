package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：选题申报分配部门的VO
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("DepartmentOptsVO")
public class DepartmentOptsVO implements Serializable {
	/**
	 * 部门id
	 */
	private Long id;
	/**
	 * 部门名称
	 */
	private String dpName;
	/**
	 * 主任名称
	 */
	private String realname;

	/**
	 * 主任企业微信id
	 *
	 * @return
	 */
	private String openid;

	/**
	 * 主任社内用户id
	 */
	private Long adminId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDpName() {
		return dpName;
	}

	public void setDpName(String dpName) {
		this.dpName = dpName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
}



