/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:添加组员作家用户视图<p>
 * <p>Description:作家用户信息展示<p>
 * @author Administrator
 * @date 2017年10月12日 下午5:45:35
 */
@SuppressWarnings("serial")
@Alias("GroupMemberWriterUserVO")
public class GroupMemberWriterUserVO implements Serializable{
	//主键
    private Long id;
    //姓名
    private String realname;
    //账号
    private String username;
    //遴选职务
    private String chosenPosition;
    //工作单位机构id
    private String workId;
    //工作单位
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

	public String getChosenPosition() {
		return chosenPosition;
	}

	public void setChosenPosition(String chosenPosition) {
		this.chosenPosition = chosenPosition;
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

	@Override
	public String toString() {
		return " {id:" + id + ", realname:" + realname + ", username:"
				+ username + ", chosenPosition:" + chosenPosition + ", workId:"
				+ workId + ", workName:" + workName + "}";
	}
     
}
