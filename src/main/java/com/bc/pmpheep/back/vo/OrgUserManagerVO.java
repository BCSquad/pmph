package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * @author MrYang
 * @CreateDate 2017年9月20日 上午9:17:38
 * 
 **/
@SuppressWarnings("serial")
@Alias("OrgUserManagerVO")
public class OrgUserManagerVO implements Serializable {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 机构代码
     */
    private String  username;
    /**
     * 是否禁用
     */
    private boolean isDisabled;
    /**
     * 对应学校ID
     */
    private Long    orgId;
    /**
     * 机构名称
     */
    private String  orgName;
    /**
     * 管理员姓名
     */
    private String  realname;
    /**
     * 职务
     */
    private String  position;
    /**
     * 职称
     */
    private String  title;
    /**
     * 手机
     */
    private String  handphone;
    /**
     * 邮箱
     */
    private String  email;
    /**
     * 地址
     */
    private String  address;
    /**
     * 邮编
     */
    private String  postcode;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String  note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "OrgUserManagerVO [id=" + id + ", username=" + username + ", isDisabled="
               + isDisabled + ", orgId=" + orgId + ", orgName=" + orgName + ", realname="
               + realname + ", position=" + position + ", title=" + title + ", handphone="
               + handphone + ", email=" + email + ", address=" + address + ", postcode=" + postcode
               + ", sort=" + sort + ", note=" + note + "]";
    }

}
