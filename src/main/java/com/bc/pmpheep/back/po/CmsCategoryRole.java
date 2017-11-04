package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("CmsCategoryRole")
public class CmsCategoryRole implements Serializable {
    private Long  id;
    private Long  categoryId;
    private Long  roleId;
    private Short permissionType;

    public CmsCategoryRole() {
    }

    public CmsCategoryRole(Long categoryId, Short permissionType) {
        super();
        this.categoryId = categoryId;
        this.permissionType = permissionType;
    }

    /**
     * <pre>
     * 构造器描述：
     *
     * @param id
     * @param cmsCategoryId
     * @param roleId
     * @param permissionType
     *</pre>
     */
    public CmsCategoryRole(Long categoryId, Long roleId, Short permissionType) {
        super();
        this.categoryId = categoryId;
        this.roleId = roleId;
        this.permissionType = permissionType;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the cmsCategoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param cmsCategoryId the cmsCategoryId to set
     */
    public void setCmsCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the roleId
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the permissionType
     */
    public Short getPermissionType() {
        return permissionType;
    }

    /**
     * @param permissionType the permissionType to set
     */
    public void setPermissionType(Short permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @Override
    public String toString() {
        return "{id:" + id + ", categoryId:" + categoryId + ", roleId:" + roleId
               + ", permissionType:" + permissionType + "}";
    }

}
