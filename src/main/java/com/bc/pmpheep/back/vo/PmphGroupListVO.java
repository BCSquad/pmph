package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * @author MrYang
 *
 * @CreateDate 2017年9月20日 下午1:51:11
 *
 **/
@SuppressWarnings("serial")
@Alias("PmphGroupListVO")
public class PmphGroupListVO implements Serializable {
	// 小组id
	private Long id;
	// 小组名称
	String groupName;
	// 小组头像地址
	private String groupImage;
	// 小组最后一条消息的时间
	private Timestamp gmtLastMessage;

	public PmphGroupListVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}

	public Timestamp getGmtLastMessage() {
		return gmtLastMessage;
	}

	public void setGmtLastMessage(Timestamp gmtLastMessage) {
		this.gmtLastMessage = gmtLastMessage;
	}

}
