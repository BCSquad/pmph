package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * PmphUserRole 实体类
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("PmphUserRole")
public class PmphUserRole implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
    

    public PmphUserRole() {
	}

	public PmphUserRole(Long userId, Long roleId) {
	this.userId = userId;
	this.roleId = roleId;
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

    public Long getRoleId() {
	return roleId;
    }

    public void setRoleId(Long roleId) {
	this.roleId = roleId;
    }

    @Override
    public String toString() {
	return "PmphUserRole [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
    }

}
