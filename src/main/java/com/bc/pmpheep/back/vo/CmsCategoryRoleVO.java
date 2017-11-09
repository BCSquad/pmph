package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("CmsCategoryRoleVO")
public class CmsCategoryRoleVO implements Serializable {
    private String categoryId;
    private String roleId;
    private Short  permissionType;

    /**
     * @return the categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {
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

}
