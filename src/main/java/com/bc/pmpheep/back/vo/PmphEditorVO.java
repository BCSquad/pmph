/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>
 * Title:PmphEditorVO
 * <p>
 * <p>
 * 部门编辑视图页面
 * <p>
 * 
 * @author Administrator
 * @date 2017年12月27日 下午3:42:13
 */

@SuppressWarnings("serial")
@Alias("PmphEditorVO")
public class PmphEditorVO implements Serializable {

	// 主键
	private Long id;
	// 部门id
	private Long departmentId;
	// 部门名称
	private String departmentName;
	// 编辑真实姓名
	private String realName;
	// 编辑电话
	private String handPhone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getHandPhone() {
		return handPhone;
	}

	public void setHandPhone(String handPhone) {
		this.handPhone = handPhone;
	}

}
