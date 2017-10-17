/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>
 * Title:添加组员作家用户视图
 * <p>
 * <p>
 * Description:作家用户信息展示
 * <p>
 * 
 * @author Administrator
 * @date 2017年10月12日 下午5:45:35
 */
@SuppressWarnings("serial")
@Alias("GroupMemberWriterUserVO")
public class GroupMemberWriterUserVO implements Serializable {
	// 主键
	private Long id;
	// 教材书籍id
	private Long bookId;
	// 姓名
	private String realname;
	// 账号
	private String username;
	// 帐号/姓名
	private String name;
	// 教材书籍
	private String bookname;
	// 遴选职务
	private Integer chosenPosition;
	// 职务名称
	private String chosenPositionName;
	// 工作单位机构id
	private String workId;
	// 工作单位
	private String workName;

	public GroupMemberWriterUserVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Integer getChosenPosition() {
		return chosenPosition;
	}

	public void setChosenPosition(Integer chosenPosition) {
		this.chosenPosition = chosenPosition;
	}

	public String getChosenPositionName() {
		return chosenPositionName;
	}

	public void setChosenPositionName(String chosenPositionName) {
		this.chosenPositionName = chosenPositionName;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return " {id:" + id + ", bookId:" + bookId + ", realname:" + realname + ", username:" + username + ", bookname:"
				+ bookname + ", chosenPosition:" + chosenPosition + "," + "chosenPositionName:" + chosenPositionName
				+ ", workId:" + workId + ", workName:" + workName + "}";
	}

}
