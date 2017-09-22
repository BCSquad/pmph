package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家简介与标签表 实体类<p>
 * <p>Description:作家简介与标签信息<p>
 * @author lyc
 * @date 2017年9月22日 上午10:38:14
 */
@SuppressWarnings("serial")
@Alias("WriterProfile")
public class WriterProfile implements java.io.Serializable {

	// 主键
	private Long id;
	// 作家id
	private Long userId;
	// 个人简介
	private String profile;
	// 个人标签
	private String tag;

	// 构造器

	/** default constructor */
	public WriterProfile() {
	}

	

	public WriterProfile(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public WriterProfile(Long userId, String profile, String tag) {
		this.userId = userId;
		this.profile = profile;
		this.tag = tag;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getProfile() {
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}



	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return " {id:" + id + ", userId:" + userId + ", profile:" + profile
				+ ", tag:" + tag + "}";
	}	

}