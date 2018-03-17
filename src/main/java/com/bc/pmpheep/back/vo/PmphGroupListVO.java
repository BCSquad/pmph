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
	// 小组书籍id
	private String bookId;
	// 小组书籍名称
	private String bookName;
	/**
	 * 该小组的文件数量
	 */
	private Integer files;
	// 判断当前用户是否为该小组成员
	private Boolean isMember;

	public PmphGroupListVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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

	public Integer getFiles() {
		return files;
	}

	public void setFiles(Integer files) {
		this.files = files;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

}
