package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：选题申报当前用户身份
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018年2月9日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("PmphIdentity")
public class PmphIdentity implements Serializable {
	private Long id;

	private String username;

	private String realname;

	private Boolean isAdmin = false;

	private Boolean isDirector = false;

	private Boolean isOpts = false;

	private Boolean isEditor = false;

	public PmphIdentity() {
		super();
	}

	public PmphIdentity(Long id, String username, String realname) {
		super();
		this.id = id;
		this.username = username;
		this.realname = realname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(Boolean isDirector) {
		this.isDirector = isDirector;
	}

	public Boolean getIsOpts() {
		return isOpts;
	}

	public void setIsOpts(Boolean isOpts) {
		this.isOpts = isOpts;
	}

	public Boolean getIsEditor() {
		return isEditor;
	}

	public void setIsEditor(Boolean isEditor) {
		this.isEditor = isEditor;
	}

}
