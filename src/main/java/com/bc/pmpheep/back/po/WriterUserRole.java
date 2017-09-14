package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * WriterUserRole 实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("WriterUserRole")
public class WriterUserRole implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 作家用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
    
    

    public WriterUserRole() {
    }

    public WriterUserRole(Long userId, Long roleId) {
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
	return "WriterUserRole [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
    }

}
