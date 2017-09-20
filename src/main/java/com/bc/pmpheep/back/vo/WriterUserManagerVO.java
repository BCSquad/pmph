package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.WriterUser;

/**
 * @author MrYang
 * @CreateDate 2017年9月20日 上午9:19:30
 *
 **/
@SuppressWarnings("serial")
@Alias("WriterUserManagerVO")
public class WriterUserManagerVO implements Serializable {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 用户名
     */
    private String  username;
    /**
     * 是否禁用
     */
    private Integer isDisabled;
    /**
     * 对应学校id
     */
    private Long    orgId;
    /**
     * 机构名称
     */
    private String  orgName;
    /**
     * 真实姓名
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
     * 级别
     */
    private Integer rank;

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

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
