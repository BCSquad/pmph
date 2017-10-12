package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：角色VO
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @date (开发日期) 2017年10月12日
 * @修改人 ：曾庆峰
 *
 */
@Alias("PmphRoleVO")
public class PmphRoleVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String roleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
