package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述： 小组成员VO
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @date (开发日期) 2017年9月29日
 * @修改人 ：曾庆峰
 *
 */
@Alias("PmphGroupMemberVO")
public class PmphGroupMemberVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 小组id
	 */
	private Long groupId;
	/**
	 * 成员id
	 */
	private Long memberId;
	/**
	 * 是否创建者
	 */
	private boolean isFounder;
	/**
	 * 是否管理员
	 */
	private boolean isAdmin;
	/**
	 * 小组显示名称
	 */
	private String displayName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public boolean isFounder() {
		return isFounder;
	}

	public void setFounder(boolean isFounder) {
		this.isFounder = isFounder;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
